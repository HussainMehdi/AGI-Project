"""
Ollama Client for LLM and Embedding Operations
"""

import requests
import json
import logging
from typing import Dict, Any, Optional

logger = logging.getLogger(__name__)


class OllamaClient:
    """Client for interacting with Ollama API"""
    
    def __init__(
        self,
        base_url: str = "http://localhost:11434",
        model: str = "llama3.2",
        embedding_model: str = "nomic-embed-text",
        timeout: int = 60
    ):
        """
        Initialize Ollama client.
        
        Args:
            base_url: Base URL of Ollama server
            model: LLM model name for generation
            embedding_model: Model name for embeddings
            timeout: Request timeout in seconds
        """
        self.base_url = base_url.rstrip('/')
        self.model = model
        self.embedding_model = embedding_model
        self.timeout = timeout
    
    def check_connection(self) -> bool:
        """Check if Ollama server is accessible"""
        try:
            response = requests.get(
                f"{self.base_url}/api/tags",
                timeout=5
            )
            return response.status_code == 200
        except Exception as e:
            logger.warning(f"Ollama connection check failed: {e}")
            return False
    
    def generate(self, prompt: str, format: str = "json") -> Optional[Dict[str, Any]]:
        """
        Generate a response from Ollama LLM.
        
        Args:
            prompt: The prompt to send
            format: Response format (json or text)
            
        Returns:
            Parsed JSON response or None on error
        """
        try:
            payload = {
                "model": self.model,
                "prompt": prompt,
                "stream": False,
                "format": format
            }
            
            response = requests.post(
                f"{self.base_url}/api/generate",
                json=payload,
                timeout=self.timeout
            )
            
            if response.status_code != 200:
                logger.error(f"Ollama API error: {response.status_code} - {response.text}")
                return None
            
            result = response.json()
            response_text = result.get("response", "").strip()
            
            if format == "json":
                # Parse JSON response
                # Remove markdown code blocks if present
                response_text = response_text.removeprefix("```json").removeprefix("```")
                response_text = response_text.removesuffix("```").strip()
                
                try:
                    return json.loads(response_text)
                except json.JSONDecodeError as e:
                    logger.error(f"Failed to parse JSON response: {e}")
                    logger.error(f"Response text: {response_text[:500]}")
                    return None
            else:
                return {"response": response_text}
                
        except requests.exceptions.RequestException as e:
            logger.error(f"Request error: {e}")
            return None
        except Exception as e:
            logger.error(f"Unexpected error: {e}")
            return None
    
    def get_embedding(self, text: str) -> Optional[list]:
        """
        Get embedding vector for text.
        
        Args:
            text: Text to embed
            
        Returns:
            Embedding vector or None on error
        """
        try:
            payload = {
                "model": self.embedding_model,
                "prompt": text
            }
            
            response = requests.post(
                f"{self.base_url}/api/embeddings",
                json=payload,
                timeout=self.timeout
            )
            
            if response.status_code != 200:
                logger.error(f"Ollama embeddings API error: {response.status_code}")
                return None
            
            result = response.json()
            return result.get("embedding")
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Request error: {e}")
            return None
        except Exception as e:
            logger.error(f"Unexpected error: {e}")
            return None

