package com.agi.assistantsdk

import com.agi.assistantsdk.models.UiNode
import com.agi.assistantsdk.models.UiSnapshot

/**
 * Engine for minimizing UI snapshots by removing redundant information
 * and keeping only valuable, actionable components.
 * 
 * This reduces token usage by:
 * 1. Removing leaf nodes that have no actions and no meaningful content
 * 2. Keeping all actionable nodes and nodes with content
 * 3. Keeping parent containers that lead to valuable nodes (to preserve nodeId paths)
 */
internal class TokenMinimizer {
    
    /**
     * Minimizes a UI snapshot by filtering out non-essential nodes
     * and keeping only actionable or meaningful components.
     */
    fun minimize(snapshot: UiSnapshot): UiSnapshot {
        // Phase 1: Mark all valuable nodes (nodes we must keep)
        val valuableNodeIds = mutableSetOf<String>()
        snapshot.nodes.forEach { node ->
            markValuableNodes(node, valuableNodeIds)
        }
        
        // Phase 2: Build minimized tree keeping only valuable nodes and their ancestors
        val minimizedNodes = snapshot.nodes.mapNotNull { node ->
            buildMinimizedNode(node, valuableNodeIds)
        }
        
        return UiSnapshot(
            screenInfo = snapshot.screenInfo,
            nodes = minimizedNodes,
            timestamp = snapshot.timestamp
        )
    }
    
    /**
     * Recursively marks nodes that are valuable (should be kept).
     * A node is valuable if it has actions or meaningful content.
     */
    private fun markValuableNodes(node: UiNode, valuableNodeIds: MutableSet<String>) {
        val isValuable = isValuableNode(node)
        
        // Mark children first
        node.children.forEach { child ->
            markValuableNodes(child, valuableNodeIds)
        }
        
        // If this node or any of its descendants is valuable, mark this node
        // (we need to keep ancestors to preserve nodeId paths)
        if (isValuable || node.children.any { valuableNodeIds.contains(it.id) }) {
            valuableNodeIds.add(node.id)
        }
    }
    
    /**
     * Checks if a node is valuable and should be kept.
     * A node is valuable if it has actions or meaningful content.
     */
    private fun isValuableNode(node: UiNode): Boolean {
        // Has actions
        if (node.actions.isNotEmpty()) {
            return true
        }
        
        // Has meaningful content
        val hasContent = !node.text.isNullOrBlank() || 
                        !node.contentDesc.isNullOrBlank() || 
                        !node.hint.isNullOrBlank()
        if (hasContent) {
            return true
        }
        
        return false
    }
    
    /**
     * Builds a minimized version of the node tree, keeping only valuable nodes
     * and their ancestors.
     */
    private fun buildMinimizedNode(node: UiNode, valuableNodeIds: Set<String>): UiNode? {
        // If this node is not valuable and has no valuable descendants, skip it
        if (!valuableNodeIds.contains(node.id)) {
            return null
        }
        
        // Build minimized children (only keep valuable ones)
        val minimizedChildren = node.children.mapNotNull { child ->
            buildMinimizedNode(child, valuableNodeIds)
        }
        
        // Return the node with minimized children
        return node.copy(children = minimizedChildren)
    }
}
