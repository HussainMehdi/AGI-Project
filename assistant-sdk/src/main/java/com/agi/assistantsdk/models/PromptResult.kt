package com.agi.assistantsdk.models

/**
 * Result of executing a natural language prompt.
 */
data class PromptResult(
    val success: Boolean,
    val executedActions: List<ActionResult>,
    val error: String? = null
)

