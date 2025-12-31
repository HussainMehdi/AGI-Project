package com.agi.assistantsdk

/**
 * Configuration for AssistantSdk.
 */
data class AssistantSdkConfig(
    /**
     * Whether to take screenshots (disabled by default for privacy).
     */
    val enableScreenshots: Boolean = false,
    
    /**
     * Set of activity class names to allow (empty = all allowed).
     */
    val allowedActivities: Set<String> = emptySet(),
    
    /**
     * Set of activity class names to deny (takes precedence over allowedActivities).
     */
    val deniedActivities: Set<String> = emptySet(),
    
    /**
     * Set of view IDs (resource names) to always mask as sensitive.
     */
    val sensitiveViewIds: Set<String> = emptySet(),
    
    /**
     * Set of view tags to always mask as sensitive.
     */
    val sensitiveViewTags: Set<String> = emptySet(),
    
    /**
     * Whether to auto-detect password fields (enabled by default).
     */
    val autoDetectPasswordFields: Boolean = true,
    
    /**
     * Ollama server URL (default: http://10.0.2.2:11434 for Android emulator,
     * use your host machine's IP for physical device).
     */
    val ollamaBaseUrl: String = "http://10.0.2.2:11434",
    
    /**
     * Ollama model name to use (default: llama3.2).
     */
    val ollamaModel: String = "llama3.2",
    
    /**
     * Timeout for LLM requests in seconds (default: 60).
     */
    val llmTimeoutSeconds: Int = 60,
    
    /**
     * RAG service base URL (optional). If set, the SDK will use RAG service
     * instead of direct Ollama calls. This reduces token usage by retrieving
     * only relevant UI components.
     * Example: "http://10.0.2.2:8000" for Android emulator,
     * or "http://192.168.1.100:8000" for physical device.
     */
    val ragServiceBaseUrl: String? = null,//"http://10.0.2.2:8000",
    
    /**
     * Session ID for RAG service (optional). Used to group UI snapshots
     * and queries together. If null, a default session is used.
     */
    val ragSessionId: String? = null
) {
    companion object {
        fun default() = AssistantSdkConfig()
    }
}

