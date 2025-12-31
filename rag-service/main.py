"""
RAG-based LLM Service for UI Assistant
Uses vector embeddings to retrieve only relevant UI components for each query,
reducing token usage and improving response quality.
"""

from fastapi import FastAPI, HTTPException, UploadFile, File
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Optional, Dict, Any
import json
import logging
from datetime import datetime

from rag_service import RAGService
from ollama_client import OllamaClient

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(title="UI Assistant RAG Service", version="1.0.0")

# CORS middleware to allow Android app to connect
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # In production, specify allowed origins
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Initialize services
rag_service = RAGService()
ollama_client = OllamaClient()


class QueryRequest(BaseModel):
    """Request model for querying with RAG"""
    user_prompt: str
    session_id: Optional[str] = None
    top_k: Optional[int] = 10  # Number of relevant UI components to retrieve


class QueryResponse(BaseModel):
    """Response model for RAG query"""
    commands: List[Dict[str, Any]]
    relevant_components: List[Dict[str, Any]]  # UI components used in context
    token_usage: Dict[str, int]  # Estimated token usage
    session_id: Optional[str] = None


class HealthResponse(BaseModel):
    """Health check response"""
    status: str
    timestamp: str
    ollama_connected: bool
    vector_db_ready: bool


@app.get("/health", response_model=HealthResponse)
async def health_check():
    """Health check endpoint"""
    try:
        ollama_connected = ollama_client.check_connection()
        vector_db_ready = rag_service.is_ready()
        
        return HealthResponse(
            status="healthy" if (ollama_connected and vector_db_ready) else "degraded",
            timestamp=datetime.now().isoformat(),
            ollama_connected=ollama_connected,
            vector_db_ready=vector_db_ready
        )
    except Exception as e:
        logger.error(f"Health check failed: {e}")
        return HealthResponse(
            status="unhealthy",
            timestamp=datetime.now().isoformat(),
            ollama_connected=False,
            vector_db_ready=False
        )


@app.post("/ingest", status_code=201)
async def ingest_ui_snapshot(file: UploadFile = File(...), session_id: Optional[str] = None):
    """
    Ingest a UI snapshot file (JSON) into the vector database.
    The file should contain a UiSnapshot in JSON format.
    """
    try:
        # Read and parse the JSON file
        content = await file.read()
        snapshot_data = json.loads(content.decode('utf-8'))
        
        # Validate it's a proper UI snapshot
        if not isinstance(snapshot_data, dict) or 'nodes' not in snapshot_data:
            raise HTTPException(
                status_code=400,
                detail="Invalid UI snapshot format. Expected JSON with 'nodes' field."
            )
        
        # Ingest into RAG service
        result = rag_service.ingest_snapshot(snapshot_data, session_id)
        
        logger.info(f"Ingested UI snapshot with {result['nodes_processed']} nodes for session {session_id}")
        
        return {
            "status": "success",
            "session_id": result.get("session_id"),
            "nodes_processed": result["nodes_processed"],
            "timestamp": datetime.now().isoformat()
        }
    except json.JSONDecodeError as e:
        raise HTTPException(status_code=400, detail=f"Invalid JSON: {str(e)}")
    except Exception as e:
        logger.error(f"Error ingesting snapshot: {e}")
        raise HTTPException(status_code=500, detail=f"Error ingesting snapshot: {str(e)}")


@app.post("/query", response_model=QueryResponse)
async def query_with_rag(request: QueryRequest):
    """
    Query the LLM using RAG to retrieve only relevant UI components.
    This reduces token usage by only including relevant context.
    """
    try:
        # Retrieve relevant UI components using RAG
        relevant_components = rag_service.retrieve_relevant_components(
            query=request.user_prompt,
            session_id=request.session_id,
            top_k=request.top_k or 10
        )
        
        if not relevant_components:
            raise HTTPException(
                status_code=404,
                detail="No relevant UI components found. Please ingest a UI snapshot first."
            )
        
        # Build focused prompt with only relevant components
        focused_prompt = rag_service.build_focused_prompt(
            user_prompt=request.user_prompt,
            relevant_components=relevant_components
        )
        
        # Send to Ollama LLM
        llm_response = ollama_client.generate(focused_prompt)
        
        if not llm_response or "commands" not in llm_response:
            raise HTTPException(
                status_code=500,
                detail="Invalid response from LLM"
            )
        
        # Estimate token usage
        token_usage = {
            "prompt_tokens": rag_service.estimate_tokens(focused_prompt),
            "components_used": len(relevant_components),
            "total_components_available": rag_service.get_total_components_count(request.session_id)
        }
        
        logger.info(
            f"Query processed: {token_usage['components_used']}/{token_usage['total_components_available']} "
            f"components used, ~{token_usage['prompt_tokens']} tokens"
        )
        
        return QueryResponse(
            commands=llm_response["commands"],
            relevant_components=relevant_components,
            token_usage=token_usage,
            session_id=request.session_id
        )
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Error processing query: {e}")
        raise HTTPException(status_code=500, detail=f"Error processing query: {str(e)}")


@app.delete("/session/{session_id}")
async def clear_session(session_id: str):
    """Clear all data for a specific session"""
    try:
        rag_service.clear_session(session_id)
        return {"status": "success", "message": f"Session {session_id} cleared"}
    except Exception as e:
        logger.error(f"Error clearing session: {e}")
        raise HTTPException(status_code=500, detail=f"Error clearing session: {str(e)}")


@app.get("/stats")
async def get_stats(session_id: Optional[str] = None):
    """Get statistics about the vector database"""
    try:
        stats = rag_service.get_stats(session_id)
        return stats
    except Exception as e:
        logger.error(f"Error getting stats: {e}")
        raise HTTPException(status_code=500, detail=f"Error getting stats: {str(e)}")


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)

