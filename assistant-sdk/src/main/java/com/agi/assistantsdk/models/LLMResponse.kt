package com.agi.assistantsdk.models

import com.google.gson.annotations.SerializedName

/**
 * Response from LLM containing commands to execute.
 */
data class LLMResponse(
    val commands: List<LLMCommand>
)

/**
 * A single command from the LLM.
 */
data class LLMCommand(
    val action: String, // "click", "longClick", "setText", "focus", "scroll", "back"
    val nodeId: String? = null, // Required for all actions except "back"
    val text: String? = null, // Required for "setText"
    val direction: String? = null, // Required for "scroll": "UP", "DOWN", "LEFT", "RIGHT"
    val amount: Int? = null // Optional for "scroll", defaults to 100
)

