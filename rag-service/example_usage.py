"""
Example usage of the RAG service
This demonstrates how to use the RAG service API.
"""

import requests
import json

# Configuration
RAG_SERVICE_URL = "http://localhost:8000"

# Example UI snapshot (simplified)
example_snapshot = {
    "screenInfo": {
        "width": 1080,
        "height": 1920,
        "density": 2.75
    },
    "nodes": [
        {
            "id": "loginButton",
            "type": "Button",
            "text": "Login",
            "contentDesc": "Login button",
            "hint": None,
            "actions": ["CLICK"],
            "bounds": {"left": 100, "top": 200, "right": 300, "bottom": 250},
            "state": {
                "enabled": True,
                "clickable": True,
                "focusable": False,
                "focused": False,
                "selected": False,
                "checked": False,
                "sensitive": False
            },
            "children": []
        },
        {
            "id": "usernameField",
            "type": "EditText",
            "text": "",
            "contentDesc": "Username input field",
            "hint": "Enter username",
            "actions": ["SET_TEXT", "FOCUS"],
            "bounds": {"left": 100, "top": 100, "right": 500, "bottom": 150},
            "state": {
                "enabled": True,
                "clickable": True,
                "focusable": True,
                "focused": False,
                "selected": False,
                "checked": False,
                "sensitive": False
            },
            "children": []
        },
        {
            "id": "passwordField",
            "type": "EditText",
            "text": "",
            "contentDesc": "Password input field",
            "hint": "Enter password",
            "actions": ["SET_TEXT", "FOCUS"],
            "bounds": {"left": 100, "top": 150, "right": 500, "bottom": 200},
            "state": {
                "enabled": True,
                "clickable": True,
                "focusable": True,
                "focused": False,
                "selected": False,
                "checked": False,
                "sensitive": True
            },
            "children": []
        }
    ],
    "timestamp": 1234567890
}


def check_health():
    """Check if the service is healthy"""
    response = requests.get(f"{RAG_SERVICE_URL}/health")
    print("Health Check:")
    print(json.dumps(response.json(), indent=2))
    print()


def ingest_snapshot(snapshot, session_id=None):
    """Ingest a UI snapshot"""
    # Convert snapshot to JSON string
    snapshot_json = json.dumps(snapshot)
    
    # Create multipart form data
    files = {
        'file': ('snapshot.json', snapshot_json, 'application/json')
    }
    data = {}
    if session_id:
        data['session_id'] = session_id
    
    response = requests.post(f"{RAG_SERVICE_URL}/ingest", files=files, data=data)
    print("Ingest Response:")
    print(json.dumps(response.json(), indent=2))
    print()
    return response.json()


def query(user_prompt, session_id=None, top_k=10):
    """Query the RAG service"""
    payload = {
        "user_prompt": user_prompt,
        "top_k": top_k
    }
    if session_id:
        payload["session_id"] = session_id
    
    response = requests.post(f"{RAG_SERVICE_URL}/query", json=payload)
    print(f"Query: '{user_prompt}'")
    print("Response:")
    print(json.dumps(response.json(), indent=2))
    print()
    return response.json()


def get_stats(session_id=None):
    """Get statistics"""
    params = {}
    if session_id:
        params['session_id'] = session_id
    
    response = requests.get(f"{RAG_SERVICE_URL}/stats", params=params)
    print("Statistics:")
    print(json.dumps(response.json(), indent=2))
    print()


if __name__ == "__main__":
    print("=" * 60)
    print("RAG Service Example Usage")
    print("=" * 60)
    print()
    
    # 1. Check health
    check_health()
    
    # 2. Ingest a UI snapshot
    session_id = "test_session_123"
    ingest_snapshot(example_snapshot, session_id=session_id)
    
    # 3. Query with different prompts
    query("Click on the login button", session_id=session_id)
    query("Enter username 'testuser'", session_id=session_id)
    query("Fill in the password field", session_id=session_id)
    
    # 4. Get statistics
    get_stats(session_id=session_id)
    
    print("=" * 60)
    print("Example completed!")
    print("=" * 60)

