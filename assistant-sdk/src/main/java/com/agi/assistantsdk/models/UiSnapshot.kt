package com.agi.assistantsdk.models

/**
 * Snapshot of the current UI state.
 */
data class UiSnapshot(
    val screenInfo: ScreenInfo,
    val nodes: List<UiNode>,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Screen information.
 */
data class ScreenInfo(
    val width: Int,
    val height: Int,
    val density: Float
)

