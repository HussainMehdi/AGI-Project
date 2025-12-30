package com.agi.assistantsdk

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.agi.assistantsdk.models.NodeState

/**
 * Utilities for masking sensitive information.
 */
internal object MaskingUtils {
    
    private const val MASKED_TEXT = "***"
    
    /**
     * Checks if a view should be marked as sensitive.
     */
    fun isSensitive(view: View, config: AssistantSdkConfig): Boolean {
        // Check explicit sensitive view IDs
        view.resources?.let { res ->
            val viewId = try {
                res.getResourceEntryName(view.id)
            } catch (e: Exception) {
                null
            }
            if (viewId != null && config.sensitiveViewIds.contains(viewId)) {
                return true
            }
        }
        
        // Check explicit sensitive tags
        view.tag?.let { tag ->
            if (tag is String && config.sensitiveViewTags.contains(tag)) {
                return true
            }
        }
        
        // Auto-detect password fields
        if (config.autoDetectPasswordFields && view is EditText) {
            val inputType = view.inputType
            // Check if inputType contains password flags
            val typeClass = inputType and android.text.InputType.TYPE_MASK_CLASS
            val typeVariation = inputType and android.text.InputType.TYPE_MASK_VARIATION
            
            if (typeClass == android.text.InputType.TYPE_CLASS_TEXT) {
                if (typeVariation == android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD ||
                    typeVariation == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ||
                    typeVariation == android.text.InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
                ) {
                    return true
                }
            }
        }
        
        return false
    }
    
    /**
     * Masks text if the node is sensitive.
     */
    fun maskText(text: String?, isSensitive: Boolean): String? {
        return if (isSensitive && !text.isNullOrBlank()) {
            MASKED_TEXT
        } else {
            text
        }
    }
    
    /**
     * Creates NodeState with sensitive flag set.
     */
    fun createNodeState(
        view: View,
        config: AssistantSdkConfig,
        enabled: Boolean,
        clickable: Boolean,
        focusable: Boolean,
        focused: Boolean,
        selected: Boolean,
        checked: Boolean
    ): NodeState {
        val sensitive = isSensitive(view, config)
        return NodeState(
            enabled = enabled,
            clickable = clickable,
            focusable = focusable,
            focused = focused,
            selected = selected,
            checked = checked,
            sensitive = sensitive
        )
    }
}

