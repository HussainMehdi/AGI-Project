# Quick Start Guide - RAG Service

## Prerequisites

1. **Install Ollama** and pull required models:
```bash
# Install Ollama (if not already installed)
# Visit https://ollama.ai for installation instructions

# Pull LLM model
ollama pull llama3.2

# Pull embedding model
ollama pull nomic-embed-text
```

2. **Start Ollama**:
```bash
ollama serve
```

## Setup

1. **Install Python dependencies**:
```bash
cd rag-service
pip install -r requirements.txt
```

2. **Start the RAG service**:
```bash
python main.py
```

The service will start on `http://localhost:8000`

## Test the Service

1. **Check health**:
```bash
curl http://localhost:8000/health
```

2. **Run example script**:
```bash
python example_usage.py
```

## Integration with Android SDK

To use the RAG service with your Android app, update your SDK configuration:

```kotlin
val config = AssistantSdkConfig(
    // ... other config ...
    ragServiceBaseUrl = "http://10.0.2.2:8000",  // For emulator
    // ragServiceBaseUrl = "http://192.168.1.100:8000",  // For physical device (use your PC's IP)
    ragSessionId = "my_app_session"  // Optional
)

AssistantSdk.init(application, config)
```

Then use the SDK as normal:
```kotlin
val result = AssistantSdk.executePrompt("Click on the login button")
```

The SDK will automatically:
1. Capture UI snapshot
2. Send to RAG service
3. Query with your prompt
4. Execute returned commands

## Benefits

- **70-90% token reduction**: Only relevant UI components are included
- **Better accuracy**: Semantic search finds relevant components
- **Scalable**: Handles large UI hierarchies efficiently

## Troubleshooting

- **Connection refused**: Ensure Ollama is running (`ollama serve`)
- **Model not found**: Pull required models (`ollama pull llama3.2`)
- **Empty results**: Ingest a UI snapshot before querying

