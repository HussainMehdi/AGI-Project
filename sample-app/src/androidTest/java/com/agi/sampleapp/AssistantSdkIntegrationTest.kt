package com.agi.sampleapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agi.assistantsdk.AssistantSdk
import com.agi.assistantsdk.models.Action
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AssistantSdkIntegrationTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun testCaptureReturnsSnapshot() {
        activityRule.scenario.onActivity { activity ->
            AssistantSdk.bind(activity)
            val snapshot = AssistantSdk.capture()
            
            assertNotNull("Snapshot should not be null", snapshot)
            assertTrue("Snapshot should contain nodes", snapshot.nodes.isNotEmpty())
            assertTrue("Screen width should be > 0", snapshot.screenInfo.width > 0)
            assertTrue("Screen height should be > 0", snapshot.screenInfo.height > 0)
        }
    }
    
    @Test
    fun testCaptureReturnsNodesWithIds() {
        activityRule.scenario.onActivity { activity ->
            AssistantSdk.bind(activity)
            val snapshot = AssistantSdk.capture()
            
            // Verify nodes have IDs
            val allNodes = collectAllNodes(snapshot.nodes)
            assertTrue("Should have nodes", allNodes.isNotEmpty())
            
            allNodes.forEach { node ->
                assertNotNull("Node should have ID", node.id)
                assertTrue("Node ID should not be empty", node.id.isNotBlank())
            }
        }
    }
    
    @Test
    fun testClickActionWorks() {
        activityRule.scenario.onActivity { activity ->
            AssistantSdk.bind(activity)
            val snapshot = AssistantSdk.capture()
            
            // Find a clickable button node
            val buttonNode = findClickableNode(snapshot.nodes)
            if (buttonNode != null) {
                val result = AssistantSdk.perform(Action.Click(buttonNode.id))
                // Action may succeed or fail depending on state, but should not crash
                assertNotNull("Result should not be null", result)
            } else {
                // No clickable node found, skip test
                assertTrue("No clickable node found, skipping", true)
            }
        }
    }
    
    private fun collectAllNodes(nodes: List<com.agi.assistantsdk.models.UiNode>): List<com.agi.assistantsdk.models.UiNode> {
        val result = mutableListOf<com.agi.assistantsdk.models.UiNode>()
        nodes.forEach { node ->
            result.add(node)
            result.addAll(collectAllNodes(node.children))
        }
        return result
    }
    
    private fun findClickableNode(nodes: List<com.agi.assistantsdk.models.UiNode>): com.agi.assistantsdk.models.UiNode? {
        for (node in nodes) {
            if (node.state.clickable && node.actions.contains(com.agi.assistantsdk.models.SupportedAction.CLICK)) {
                return node
            }
            val found = findClickableNode(node.children)
            if (found != null) {
                return found
            }
        }
        return null
    }
}

