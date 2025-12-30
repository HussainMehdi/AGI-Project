package com.agi.assistantsdk.models

/**
 * Represents an action that can be performed on a UI node.
 */
sealed class Action {
    /**
     * Click action on a node.
     */
    data class Click(val nodeId: String) : Action()

    /**
     * Long click action on a node.
     */
    data class LongClick(val nodeId: String) : Action()

    /**
     * Set text action on a node (for EditText/TextView).
     */
    data class SetText(val nodeId: String, val text: String) : Action()

    /**
     * Focus action on a node.
     */
    data class Focus(val nodeId: String) : Action()

    /**
     * Scroll action on a scrollable node.
     */
    data class Scroll(
        val nodeId: String,
        val direction: ScrollDirection,
        val amount: Int = 100
    ) : Action()

    /**
     * Back button action (system back).
     */
    object Back : Action()
}

/**
 * Direction for scroll actions.
 */
enum class ScrollDirection {
    UP, DOWN, LEFT, RIGHT
}

