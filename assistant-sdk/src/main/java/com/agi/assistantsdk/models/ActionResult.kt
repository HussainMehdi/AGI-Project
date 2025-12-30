package com.agi.assistantsdk.models

/**
 * Result of performing an action.
 */
data class ActionResult(
    val success: Boolean,
    val code: ActionCode,
    val message: String? = null
) {
    companion object {
        fun success() = ActionResult(true, ActionCode.SUCCESS)
        fun failure(code: ActionCode, message: String? = null) = ActionResult(false, code, message)
    }
}

/**
 * Result codes for actions.
 */
enum class ActionCode {
    SUCCESS,
    NODE_NOT_FOUND,
    NODE_NOT_VISIBLE,
    NODE_NOT_ENABLED,
    ACTION_NOT_SUPPORTED,
    INVALID_INPUT,
    INTERNAL_ERROR
}

