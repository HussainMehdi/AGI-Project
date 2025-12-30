package com.agi.assistantsdk.models

import android.graphics.Rect

/**
 * Represents a UI node in the view hierarchy.
 */
data class UiNode(
    val id: String,
    val type: String,
    val text: String? = null,
    val contentDesc: String? = null,
    val hint: String? = null,
    val bounds: Rect,
    val state: NodeState,
    val actions: List<SupportedAction>,
    val children: List<UiNode> = emptyList()
)

/**
 * State of a UI node.
 */
data class NodeState(
    val enabled: Boolean = true,
    val clickable: Boolean = false,
    val focusable: Boolean = false,
    val focused: Boolean = false,
    val selected: Boolean = false,
    val checked: Boolean = false,
    val sensitive: Boolean = false
)

/**
 * Actions supported by a node.
 */
enum class SupportedAction {
    CLICK,
    LONG_CLICK,
    SET_TEXT,
    FOCUS,
    SCROLL
}

