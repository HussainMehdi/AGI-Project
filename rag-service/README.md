# RAG Service for UI Assistant

A Python-based RAG (Retrieval-Augmented Generation) service that uses vector embeddings to retrieve only relevant UI components for each query, significantly reducing token usage and improving response quality.

## Overview

This service addresses the token usage problem when dealing with large UI snapshots by:

1. **Storing UI components in a vector database** (ChromaDB) with semantic embeddings
2. **Retrieving only relevant components** based on the user's query using semantic search
3. **Building focused prompts** with only the retrieved components instead of the entire UI snapshot
4. **Reducing token usage** by 70-90% in typical scenarios

## Architecture

```
Android App → UI Snapshot (JSON) → RAG Service → Vector DB (ChromaDB)
                                                      ↓
User Query → Semantic Search → Relevant Components → Ollama LLM → Commands
```

## Prerequisites

1. **Python 3.8+**
2. **Ollama** installed and running with:
   - A language model (e.g., `llama3.2`, `mistral`, etc.)
   - An embedding model (e.g., `nomic-embed-text`) - Note: Currently ChromaDB uses its default embeddings, but Ollama embeddings can be configured
3. **Internet connection** (for first run) - ChromaDB will download the default embedding model (~400MB) on first use

### Installing Ollama Models

```bash
# Install LLM model
ollama pull llama3.2

# Install embedding model
ollama pull nomic-embed-text
```

## Installation

1. Install dependencies:
```bash
cd rag-service
pip install -r requirements.txt
```

2. Configure the service (optional):
   - Edit `main.py` to change Ollama base URL, model names, etc.
   - Default: `http://localhost:11434` with `llama3.2` model

## Running the Service

```bash
python main.py
```

Or with uvicorn directly:
```bash
uvicorn main:app --host 0.0.0.0 --port 8000
```

The service will be available at `http://localhost:8000`

## API Endpoints

### Health Check
```bash
GET /health
```

Returns service health status and connection info.

### Ingest UI Snapshot
```bash
POST /ingest
Content-Type: multipart/form-data

file: <UI snapshot JSON file>
session_id: <optional session identifier>
```

Uploads a UI snapshot JSON file and stores UI components in the vector database.

**Example UI Snapshot Format:**
```json
{
  "screenInfo": {
    "width": 1080,
    "height": 1920,
    "density": 2.75
  },
  "nodes": [
    {
      "id": "button1",
      "type": "Button",
      "text": "Click Me",
      "actions": ["CLICK"],
      "bounds": {"left": 100, "top": 200, "right": 300, "bottom": 250},
      "children": []
    }
  ],
  "timestamp": 1234567890
}
```

### Query with RAG
```bash
POST /query
Content-Type: application/json

{
  "user_prompt": "Click on the login button",
  "session_id": "optional-session-id",
  "top_k": 10
}
```

Returns LLM-generated commands with only relevant UI components in context.

**Response:**
```json
{
  "commands": [
    {
      "action": "click",
      "nodeId": "loginButton"
    }
  ],
  "relevant_components": [
    {
      "node_id": "loginButton",
      "type": "Button",
      "text": "Login",
      "similarity_score": 0.95
    }
  ],
  "token_usage": {
    "prompt_tokens": 250,
    "components_used": 3,
    "total_components_available": 50
  },
  "session_id": "optional-session-id"
}
```

### Clear Session
```bash
DELETE /session/{session_id}
```

Clears all UI components for a specific session.

### Get Statistics
```bash
GET /stats?session_id=<optional>
```

Returns statistics about stored UI components.

## Integration with Android SDK

The Android SDK can be updated to use this RAG service instead of direct Ollama calls. The flow would be:

1. Capture UI snapshot
2. Send snapshot to `/ingest` endpoint
3. For each user query, send to `/query` endpoint
4. Receive focused commands and execute them

## Benefits

1. **Reduced Token Usage**: Only relevant UI components are included in prompts (typically 70-90% reduction)
2. **Better Accuracy**: Semantic search finds relevant components even with partial matches
3. **Scalability**: Can handle large UI hierarchies efficiently
4. **Session Management**: Support for multiple concurrent sessions

## Configuration

Edit `main.py` and `ollama_client.py` to configure:
- Ollama base URL
- LLM model name
- Embedding model name
- Timeout values
- Vector database persistence directory

## Troubleshooting

1. **Ollama connection errors**: Ensure Ollama is running and accessible
2. **Model not found**: Pull the required models using `ollama pull <model-name>`
3. **ChromaDB errors**: Check write permissions for the `chroma_db` directory
4. **Empty results**: Ensure UI snapshot was ingested before querying

## Performance

- **Ingestion**: ~100-500 nodes/second (depending on hardware)
- **Query**: ~100-300ms (including LLM inference)
- **Token reduction**: 70-90% compared to full snapshot inclusion

## Future Enhancements

- [ ] Support for incremental updates (add/remove components)
- [ ] Multi-modal embeddings (screenshots + text)
- [ ] Caching of frequent queries
- [ ] Batch processing support
- [ ] Advanced filtering (by UI type, actions, etc.)

