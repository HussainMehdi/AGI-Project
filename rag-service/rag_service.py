"""
RAG Service for UI Component Retrieval
Uses ChromaDB to store and retrieve relevant UI components based on semantic similarity.
"""

import chromadb
from chromadb.config import Settings
import json
import logging
from typing import List, Dict, Any, Optional
import os
from pathlib import Path

logger = logging.getLogger(__name__)


class RAGService:
    """Service for RAG-based retrieval of UI components"""
    
    def __init__(self, persist_directory: str = "./chroma_db", embedding_model: str = "nomic-embed-text"):
        """
        Initialize the RAG service with ChromaDB.
        
        Args:
            persist_directory: Directory to persist ChromaDB data
            embedding_model: Ollama embedding model name
        """
        self.persist_directory = persist_directory
        self.embedding_model = embedding_model
        
        # Create persist directory if it doesn't exist
        Path(persist_directory).mkdir(parents=True, exist_ok=True)
        
        # Initialize ChromaDB client
        self.client = chromadb.PersistentClient(
            path=persist_directory,
            settings=Settings(anonymized_telemetry=False)
        )
        
        # Get or create collection
        self.collection = self.client.get_or_create_collection(
            name="ui_components",
            metadata={"hnsw:space": "cosine"}
        )
        
        logger.info(f"RAG Service initialized with collection: {self.collection.name}")
    
    def is_ready(self) -> bool:
        """Check if the service is ready"""
        try:
            return self.collection is not None
        except Exception:
            return False
    
    def ingest_snapshot(self, snapshot: Dict[str, Any], session_id: Optional[str] = None) -> Dict[str, Any]:
        """
        Ingest a UI snapshot into the vector database.
        Each UI node is stored as a separate document with its metadata.
        
        Args:
            snapshot: UI snapshot dictionary with 'nodes' field
            session_id: Optional session identifier
            
        Returns:
            Dictionary with ingestion results
        """
        nodes = snapshot.get("nodes", [])
        if not nodes:
            raise ValueError("Snapshot has no nodes")
        
        documents = []
        metadatas = []
        ids = []
        
        node_count = 0
        
        def process_node(node: Dict[str, Any], parent_path: str = ""):
            """Recursively process UI nodes"""
            nonlocal node_count
            
            node_id = node.get("id", f"node_{node_count}")
            node_type = node.get("type", "Unknown")
            text = node.get("text", "")
            content_desc = node.get("contentDesc", "")
            hint = node.get("hint", "")
            actions = node.get("actions", [])
            bounds = node.get("bounds", {})
            
            # Build a searchable text representation
            searchable_text_parts = []
            if text:
                searchable_text_parts.append(f"Text: {text}")
            if content_desc:
                searchable_text_parts.append(f"Description: {content_desc}")
            if hint:
                searchable_text_parts.append(f"Hint: {hint}")
            if node_type:
                searchable_text_parts.append(f"Type: {node_type}")
            if actions:
                action_names = [a.get("name", a) if isinstance(a, dict) else str(a) for a in actions]
                searchable_text_parts.append(f"Actions: {', '.join(action_names)}")
            
            searchable_text = " | ".join(searchable_text_parts)
            
            if not searchable_text.strip():
                # Skip nodes with no searchable content
                pass
            else:
                # Create document text for embedding
                document_text = f"{node_type} {searchable_text}"
                
                # Create metadata
                metadata = {
                    "node_id": node_id,
                    "type": node_type,
                    "text": text or "",
                    "content_desc": content_desc or "",
                    "hint": hint or "",
                    "actions": json.dumps(actions),
                    "bounds": json.dumps(bounds),
                    "parent_path": parent_path,
                    "session_id": session_id or "default",
                    "full_node": json.dumps(node)  # Store full node for retrieval
                }
                
                documents.append(document_text)
                metadatas.append(metadata)
                ids.append(f"{session_id or 'default'}_{node_id}_{node_count}")
                node_count += 1
            
            # Process children
            children = node.get("children", [])
            current_path = f"{parent_path}/{node_id}" if parent_path else node_id
            for child in children:
                process_node(child, current_path)
        
        # Process all root nodes
        for root_node in nodes:
            process_node(root_node)
        
        if not documents:
            raise ValueError("No searchable nodes found in snapshot")
        
        # Add to ChromaDB
        self.collection.add(
            documents=documents,
            metadatas=metadatas,
            ids=ids
        )
        
        logger.info(f"Ingested {node_count} nodes into vector database")
        
        return {
            "nodes_processed": node_count,
            "session_id": session_id or "default"
        }
    
    def retrieve_relevant_components(
        self,
        query: str,
        session_id: Optional[str] = None,
        top_k: int = 10
    ) -> List[Dict[str, Any]]:
        """
        Retrieve relevant UI components using semantic search.
        
        Args:
            query: User's natural language query
            session_id: Optional session identifier to filter results
            top_k: Number of components to retrieve
            
        Returns:
            List of relevant UI components with their metadata
        """
        # Build query text
        query_text = f"UI component: {query}"
        
        # Query the collection
        where_clause = {"session_id": session_id or "default"} if session_id else None
        
        results = self.collection.query(
            query_texts=[query_text],
            n_results=min(top_k, self.collection.count()),
            where=where_clause
        )
        
        if not results["ids"] or not results["ids"][0]:
            return []
        
        # Extract relevant components
        relevant_components = []
        for i, node_id in enumerate(results["ids"][0]):
            metadata = results["metadatas"][0][i]
            distance = results["distances"][0][i] if results["distances"] else None
            
            # Parse full node from metadata
            try:
                full_node = json.loads(metadata.get("full_node", "{}"))
            except:
                full_node = {}
            
            component = {
                "node_id": metadata.get("node_id"),
                "type": metadata.get("type"),
                "text": metadata.get("text"),
                "content_desc": metadata.get("content_desc"),
                "hint": metadata.get("hint"),
                "actions": json.loads(metadata.get("actions", "[]")),
                "bounds": json.loads(metadata.get("bounds", "{}")),
                "similarity_score": 1 - distance if distance is not None else None,
                "full_node": full_node
            }
            
            relevant_components.append(component)
        
        logger.info(f"Retrieved {len(relevant_components)} relevant components for query: {query[:50]}...")
        
        return relevant_components
    
    def build_focused_prompt(
        self,
        user_prompt: str,
        relevant_components: List[Dict[str, Any]]
    ) -> str:
        """
        Build a focused prompt with only relevant UI components.
        This significantly reduces token usage compared to including all components.
        """
        # Build UI description from relevant components
        components_description = []
        for comp in relevant_components:
            comp_lines = [
                f"- nodeId: \"{comp['node_id']}\"",
                f"  type: {comp['type']}"
            ]
            
            if comp.get('text'):
                comp_lines.append(f"  text: \"{comp['text']}\"")
            if comp.get('content_desc'):
                comp_lines.append(f"  description: \"{comp['content_desc']}\"")
            if comp.get('hint'):
                comp_lines.append(f"  hint: \"{comp['hint']}\"")
            if comp.get('actions'):
                actions_str = ', '.join([str(a) for a in comp['actions']])
                comp_lines.append(f"  actions: [{actions_str}]")
            
            bounds = comp.get('bounds', {})
            if bounds:
                comp_lines.append(
                    f"  bounds: [{bounds.get('left', 0)}, {bounds.get('top', 0)}, "
                    f"{bounds.get('right', 0)}, {bounds.get('bottom', 0)}]"
                )
            
            components_description.append("\n".join(comp_lines))
        
        ui_description = "\n\n".join(components_description)
        
        # Build the prompt
        prompt = f"""You are an Android UI automation assistant. You have access to relevant UI components and need to execute actions based on the user's request.

RELEVANT UI COMPONENTS (retrieved using semantic search):
{ui_description}

USER REQUEST: {user_prompt}

AVAILABLE ACTIONS:
- click: Click on a UI element (requires nodeId)
- longClick: Long press on a UI element (requires nodeId)
- setText: Set text in an input field (requires nodeId and text)
- focus: Focus on a UI element (requires nodeId)
- scroll: Scroll a scrollable view (requires nodeId, direction: UP/DOWN/LEFT/RIGHT, optional amount)
- back: Navigate back (no parameters needed)

INSTRUCTIONS:
1. Analyze the user's request and the relevant UI components above
2. Identify which UI elements need to be interacted with (use the nodeId from the UI components)
3. Determine the appropriate actions to execute
4. Return a JSON response with the following structure:
   {{
     "commands": [
       {{
         "action": "click",
         "nodeId": "sampleButton"
       }}
     ]
   }}

5. For actions that require multiple steps, include all commands in order
6. Use exact nodeId values from the UI components above
7. For setText, include both nodeId and text fields
8. For scroll, include nodeId, direction (UP/DOWN/LEFT/RIGHT), and optionally amount (default 100)
9. For back action, omit nodeId

RESPONSE FORMAT (must be valid JSON only, no markdown, no explanations):
{{
  "commands": [
    {{
      "action": "click",
      "nodeId": "sampleButton"
    }}
  ]
}}

Now, based on the user's request above, return the JSON commands to execute:"""
        
        return prompt
    
    def estimate_tokens(self, text: str) -> int:
        """
        Rough estimation of token count.
        Uses a simple heuristic: ~4 characters per token.
        """
        return len(text) // 4
    
    def get_total_components_count(self, session_id: Optional[str] = None) -> int:
        """Get total number of components in the database for a session"""
        if session_id:
            # Count with filter
            try:
                results = self.collection.get(
                    where={"session_id": session_id or "default"}
                )
                return len(results["ids"]) if results["ids"] else 0
            except:
                return 0
        return self.collection.count()
    
    def clear_session(self, session_id: str):
        """Clear all components for a specific session"""
        try:
            # Delete all documents for this session
            results = self.collection.get(
                where={"session_id": session_id}
            )
            if results["ids"]:
                self.collection.delete(ids=results["ids"])
            logger.info(f"Cleared session {session_id}: {len(results['ids']) if results['ids'] else 0} components")
        except Exception as e:
            logger.error(f"Error clearing session {session_id}: {e}")
            raise
    
    def get_stats(self, session_id: Optional[str] = None) -> Dict[str, Any]:
        """Get statistics about the vector database"""
        try:
            total_count = self.collection.count()
            session_count = 0
            
            if session_id:
                results = self.collection.get(
                    where={"session_id": session_id}
                )
                session_count = len(results["ids"]) if results["ids"] else 0
            
            return {
                "total_components": total_count,
                "session_components": session_count if session_id else total_count,
                "session_id": session_id or "all"
            }
        except Exception as e:
            logger.error(f"Error getting stats: {e}")
            return {"error": str(e)}

