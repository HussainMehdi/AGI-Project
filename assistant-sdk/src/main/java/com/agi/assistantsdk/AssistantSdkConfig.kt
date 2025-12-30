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
    val autoDetectPasswordFields: Boolean = true
) {
    companion object {
        fun default() = AssistantSdkConfig()
    }
}

