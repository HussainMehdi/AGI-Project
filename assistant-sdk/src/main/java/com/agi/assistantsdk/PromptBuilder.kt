package com.agi.assistantsdk

import com.agi.assistantsdk.models.UiNode
import com.agi.assistantsdk.models.UiSnapshot
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Builds prompts for the LLM from UI snapshots.
 */
internal class PromptBuilder {
    
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    
    /**
     * Builds a prompt that includes the UI snapshot and user request.
     */
    fun buildPrompt(userPrompt: String, snapshot: UiSnapshot): String {
        val uiDescription = buildUiDescription(snapshot)
        
        return """
You are an Android UI automation assistant. You have access to the current UI state and need to execute actions based on the user's request.

CURRENT UI STATE:
$uiDescription

USER REQUEST: $userPrompt

AVAILABLE ACTIONS:
- click: Click on a UI element (requires nodeId)
- longClick: Long press on a UI element (requires nodeId)
- setText: Set text in an input field (requires nodeId and text)
- focus: Focus on a UI element (requires nodeId)
- scroll: Scroll a scrollable view (requires nodeId, direction: UP/DOWN/LEFT/RIGHT, optional amount)
- back: Navigate back (no parameters needed)

INSTRUCTIONS:
1. Analyze the user's request and the current UI state
2. Identify which UI elements need to be interacted with (use the nodeId from the UI state)
3. Determine the appropriate actions to execute
4. Return a JSON response with the following structure:
   {
     "commands": [
       {
         "action": "click",
         "nodeId": "sampleButton"
       }
     ]
   }

5. For actions that require multiple steps, include all commands in order
6. Use exact nodeId values from the UI state (they may be resource names like "sampleButton" or paths like "ConstraintLayout[0]/Button[0]")
7. For setText, include both nodeId and text fields
8. For scroll, include nodeId, direction (UP/DOWN/LEFT/RIGHT), and optionally amount (default 100)
9. For back action, omit nodeId

RESPONSE FORMAT (must be valid JSON only, no markdown, no explanations):
{
  "commands": [
    {
      "action": "click",
      "nodeId": "sampleButton"
    }
  ]
}

Now, based on the user's request above, return the JSON commands to execute:
""".trimIndent()
    }
    
    /**
     * Builds a human-readable description of the UI state.
     */
    private fun buildUiDescription(snapshot: UiSnapshot): String {
        val nodesDescription = snapshot.nodes.joinToString("\n\n") { node ->
            buildNodeDescription(node, 0)
        }
        
        return """
Screen: ${snapshot.screenInfo.width}x${snapshot.screenInfo.height} (density: ${snapshot.screenInfo.density})

UI Elements:
$nodesDescription
""".trimIndent()
    }
    
    /**
     * Builds a description for a single node.
     */
    private fun buildNodeDescription(node: UiNode, depth: Int): String {
        val indent = "  ".repeat(depth)
        val bounds = node.bounds
        val actions = node.actions.joinToString(", ") { it.name }
        
        val stateInfo = buildList {
            if (!node.state.enabled) add("disabled")
            if (node.state.focused) add("focused")
            if (node.state.selected) add("selected")
            if (node.state.checked) add("checked")
        }.joinToString(", ").takeIf { it.isNotEmpty() }?.let { " [$it]" } ?: ""
        
        val textInfo = node.text?.let { " (text: \"$it\")" } ?: ""
        val hintInfo = node.hint?.let { " (hint: \"$it\")" } ?: ""
        val contentDescInfo = node.contentDesc?.let { " (desc: \"$it\")" } ?: ""
        
        val nodeLine = buildString {
            append("$indent- nodeId: \"${node.id}\"")
            append(" | type: ${node.type}")
            append(" | bounds: [${bounds.left}, ${bounds.top}, ${bounds.right}, ${bounds.bottom}]")
            append(" | actions: [$actions]")
            append(stateInfo)
            append(textInfo)
            append(hintInfo)
            append(contentDescInfo)
        }
        
        val childrenDescription = if (node.children.isNotEmpty()) {
            "\n" + node.children.joinToString("\n") { child ->
                buildNodeDescription(child, depth + 1)
            }
        } else {
            ""
        }
        
        return nodeLine + childrenDescription
    }
}

