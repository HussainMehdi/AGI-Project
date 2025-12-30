package com.agi.assistantsdk

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.NestedScrollView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.agi.assistantsdk.models.Action
import com.agi.assistantsdk.models.ActionCode
import com.agi.assistantsdk.models.ActionResult
import com.agi.assistantsdk.models.ScrollDirection

/**
 * Executes actions on UI nodes.
 */
internal class ActionExecutor(
    private val captureEngine: ViewCaptureEngine
) {
    private val mainHandler = Handler(Looper.getMainLooper())
    
    fun execute(activity: Activity, action: Action): ActionResult {
        return when (action) {
            is Action.Back -> executeBack(activity)
            is Action.Click -> executeClick(action.nodeId)
            is Action.LongClick -> executeLongClick(action.nodeId)
            is Action.SetText -> executeSetText(action.nodeId, action.text)
            is Action.Focus -> executeFocus(action.nodeId)
            is Action.Scroll -> executeScroll(action.nodeId, action.direction, action.amount)
        }
    }
    
    private fun executeBack(activity: Activity): ActionResult {
        return try {
            mainHandler.post {
                activity.onBackPressedDispatcher.onBackPressed()
            }
            ActionResult.success()
        } catch (e: Exception) {
            ActionResult.failure(ActionCode.INTERNAL_ERROR, e.message)
        }
    }
    
    private fun executeClick(nodeId: String): ActionResult {
        val view = captureEngine.getViewForNodeId(nodeId) ?: return ActionResult.failure(
            ActionCode.NODE_NOT_FOUND, "Node not found: $nodeId"
        )
        
        return validateAndExecute(view) {
            mainHandler.post {
                view.performClick()
            }
            ActionResult.success()
        }
    }
    
    private fun executeLongClick(nodeId: String): ActionResult {
        val view = captureEngine.getViewForNodeId(nodeId) ?: return ActionResult.failure(
            ActionCode.NODE_NOT_FOUND, "Node not found: $nodeId"
        )
        
        return validateAndExecute(view) {
            mainHandler.post {
                view.performLongClick()
            }
            ActionResult.success()
        }
    }
    
    private fun executeSetText(nodeId: String, text: String): ActionResult {
        val view = captureEngine.getViewForNodeId(nodeId) ?: return ActionResult.failure(
            ActionCode.NODE_NOT_FOUND, "Node not found: $nodeId"
        )
        
        if (view !is EditText) {
            return ActionResult.failure(
                ActionCode.ACTION_NOT_SUPPORTED, "Node does not support setText"
            )
        }
        
        return validateAndExecute(view) {
            mainHandler.post {
                when (view) {
                    is EditText -> {
                        view.setText(text)
                        view.setSelection(text.length)
                    }
                    else -> throw IllegalArgumentException("View does not support setText")
                }
            }
            ActionResult.success()
        }
    }
    
    private fun executeFocus(nodeId: String): ActionResult {
        val view = captureEngine.getViewForNodeId(nodeId) ?: return ActionResult.failure(
            ActionCode.NODE_NOT_FOUND, "Node not found: $nodeId"
        )
        
        return validateAndExecute(view) {
            mainHandler.post {
                view.requestFocus()
            }
            ActionResult.success()
        }
    }
    
    private fun executeScroll(nodeId: String, direction: ScrollDirection, amount: Int): ActionResult {
        val view = captureEngine.getViewForNodeId(nodeId) ?: return ActionResult.failure(
            ActionCode.NODE_NOT_FOUND, "Node not found: $nodeId"
        )
        
        return validateAndExecute(view) {
            mainHandler.post {
                when (view) {
                    is ScrollView -> {
                        val scrollAmount = when (direction) {
                            ScrollDirection.DOWN -> amount
                            ScrollDirection.UP -> -amount
                            else -> 0
                        }
                        view.smoothScrollBy(0, scrollAmount)
                    }
                    is NestedScrollView -> {
                        val scrollAmount = when (direction) {
                            ScrollDirection.DOWN -> amount
                            ScrollDirection.UP -> -amount
                            else -> 0
                        }
                        view.smoothScrollBy(0, scrollAmount)
                    }
                    is RecyclerView -> {
                        val layoutManager = view.layoutManager
                        val adapter = view.adapter
                        if (layoutManager != null && adapter != null) {
                            val currentPosition = when {
                                layoutManager.canScrollVertically(1) -> {
                                    val firstVisible = layoutManager.findFirstVisibleItemPosition()
                                    when (direction) {
                                        ScrollDirection.DOWN -> (firstVisible + 1).coerceAtMost(adapter.itemCount - 1)
                                        ScrollDirection.UP -> (firstVisible - 1).coerceAtLeast(0)
                                        else -> firstVisible
                                    }
                                }
                                layoutManager.canScrollHorizontally(1) -> {
                                    val firstVisible = layoutManager.findFirstVisibleItemPosition()
                                    when (direction) {
                                        ScrollDirection.RIGHT -> (firstVisible + 1).coerceAtMost(adapter.itemCount - 1)
                                        ScrollDirection.LEFT -> (firstVisible - 1).coerceAtLeast(0)
                                        else -> firstVisible
                                    }
                                }
                                else -> 0
                            }
                            view.smoothScrollToPosition(currentPosition)
                        } else {
                            view.scrollBy(
                                when (direction) {
                                    ScrollDirection.RIGHT -> amount
                                    ScrollDirection.LEFT -> -amount
                                    else -> 0
                                },
                                when (direction) {
                                    ScrollDirection.DOWN -> amount
                                    ScrollDirection.UP -> -amount
                                    else -> 0
                                }
                            )
                        }
                    }
                    else -> throw IllegalArgumentException("View does not support scrolling")
                }
            }
            ActionResult.success()
        }
    }
    
    private fun validateAndExecute(
        view: View,
        action: () -> ActionResult
    ): ActionResult {
        if (!view.isShown) {
            return ActionResult.failure(ActionCode.NODE_NOT_VISIBLE, "Node is not visible")
        }
        
        if (!view.isEnabled) {
            return ActionResult.failure(ActionCode.NODE_NOT_ENABLED, "Node is not enabled")
        }
        
        return try {
            action()
        } catch (e: Exception) {
            ActionResult.failure(ActionCode.INTERNAL_ERROR, e.message)
        }
    }
}

