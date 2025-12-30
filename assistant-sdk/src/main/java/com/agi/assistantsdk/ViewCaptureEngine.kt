package com.agi.assistantsdk

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.agi.assistantsdk.models.ScreenInfo
import com.agi.assistantsdk.models.SupportedAction
import com.agi.assistantsdk.models.UiNode
import com.agi.assistantsdk.models.UiSnapshot

/**
 * Engine for capturing UI state from Views.
 */
internal class ViewCaptureEngine(
    private val config: AssistantSdkConfig,
    private val idGenerator: NodeIdGenerator
) {
    
    private val viewIdMap = mutableMapOf<String, View>()
    
    fun capture(activity: Activity): UiSnapshot {
        viewIdMap.clear()
        
        val rootView = activity.window.decorView.rootView
        val displayMetrics = activity.resources.displayMetrics
        
        val screenInfo = ScreenInfo(
            width = displayMetrics.widthPixels,
            height = displayMetrics.heightPixels,
            density = displayMetrics.density
        )
        
        val nodes = traverseView(rootView, "")
        
        return UiSnapshot(
            screenInfo = screenInfo,
            nodes = nodes,
            timestamp = System.currentTimeMillis()
        )
    }
    
    fun getViewForNodeId(nodeId: String): View? {
        return viewIdMap[nodeId]
    }
    
    private fun traverseView(view: View, parentPath: String): List<UiNode> {
        val nodes = mutableListOf<UiNode>()
        
        // Skip if not shown
        if (!view.isShown) {
            return nodes
        }
        
        // Check global visible rect
        val visibleRect = Rect()
        val isVisible = view.getGlobalVisibleRect(visibleRect) && 
                       visibleRect.width() > 0 && 
                       visibleRect.height() > 0
        
        if (!isVisible) {
            return nodes
        }
        
        // Generate stable ID
        val nodeId = idGenerator.generateId(view, parentPath)
        viewIdMap[nodeId] = view
        
        // Extract text content
        val text = when (view) {
            is TextView -> view.text?.toString()
            else -> null
        }
        
        val contentDesc = view.contentDescription?.toString()
        val hint = if (view is EditText) view.hint?.toString() else null
        
        // Extract state
        val enabled = view.isEnabled
        val clickable = view.isClickable || view.isLongClickable
        val focusable = view.isFocusable
        val focused = view.isFocused
        val selected = view.isSelected
        val checked = if (view is Checkable) view.isChecked else false
        
        val nodeState = MaskingUtils.createNodeState(
            view, config, enabled, clickable, focusable, focused, selected, checked
        )
        
        // Determine supported actions
        val actions = mutableListOf<SupportedAction>()
        if (clickable) {
            actions.add(SupportedAction.CLICK)
            if (view.isLongClickable) {
                actions.add(SupportedAction.LONG_CLICK)
            }
        }
        if (view is EditText) {
            actions.add(SupportedAction.SET_TEXT)
        }
        if (focusable) {
            actions.add(SupportedAction.FOCUS)
        }
        if (view is ScrollView || view is NestedScrollView || view is RecyclerView) {
            actions.add(SupportedAction.SCROLL)
        }
        
        // Mask sensitive text
        val maskedText = MaskingUtils.maskText(text, nodeState.sensitive)
        val maskedContentDesc = MaskingUtils.maskText(contentDesc, nodeState.sensitive)
        val maskedHint = MaskingUtils.maskText(hint, nodeState.sensitive)
        
        // Traverse children
        val children = if (view is ViewGroup) {
            val currentPath = if (parentPath.isEmpty()) nodeId else "$parentPath/$nodeId"
            val childNodes = mutableListOf<UiNode>()
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                childNodes.addAll(traverseView(child, currentPath))
            }
            childNodes
        } else {
            emptyList()
        }
        
        // Create node
        val node = UiNode(
            id = nodeId,
            type = view.javaClass.simpleName,
            text = maskedText,
            contentDesc = maskedContentDesc,
            hint = maskedHint,
            bounds = visibleRect,
            state = nodeState,
            actions = actions,
            children = children
        )
        
        nodes.add(node)
        return nodes
    }
}

/**
 * Generates stable IDs for views.
 */
internal class NodeIdGenerator {
    fun generateId(view: View, parentPath: String): String {
        // Try to use resource ID name first (most stable)
        val resourceIdName = try {
            view.resources?.getResourceEntryName(view.id)
        } catch (_: Exception) {
            null
        }
        
        if (!resourceIdName.isNullOrBlank() && view.id != View.NO_ID) {
            return resourceIdName
        }
        
        // Fall back to class name + index path
        val className = view.javaClass.simpleName
        val index = getViewIndexInParent(view)
        val path = if (parentPath.isEmpty()) {
            "$className[$index]"
        } else {
            "$parentPath/$className[$index]"
        }
        
        return path
    }
    
    private fun getViewIndexInParent(view: View): Int {
        val parent = view.parent
        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                if (parent.getChildAt(i) == view) {
                    return i
                }
            }
        }
        return 0
    }
}
