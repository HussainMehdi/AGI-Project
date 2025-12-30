package com.agi.assistantsdk

import com.agi.assistantsdk.models.Action
import com.agi.assistantsdk.models.LLMCommand
import com.agi.assistantsdk.models.ScrollDirection

/**
 * Parses LLM commands into Action objects.
 */
internal class LLMCommandParser {
    
    /**
     * Converts LLM commands to Action objects.
     */
    fun parseCommands(commands: List<LLMCommand>): List<Action> {
        return commands.mapNotNull { command ->
            try {
                when (command.action.lowercase()) {
                    "click" -> {
                        val nodeId = command.nodeId ?: return@mapNotNull null
                        Action.Click(nodeId)
                    }
                    "longclick", "long_click" -> {
                        val nodeId = command.nodeId ?: return@mapNotNull null
                        Action.LongClick(nodeId)
                    }
                    "settext", "set_text" -> {
                        val nodeId = command.nodeId ?: return@mapNotNull null
                        val text = command.text ?: return@mapNotNull null
                        Action.SetText(nodeId, text)
                    }
                    "focus" -> {
                        val nodeId = command.nodeId ?: return@mapNotNull null
                        Action.Focus(nodeId)
                    }
                    "scroll" -> {
                        val nodeId = command.nodeId ?: return@mapNotNull null
                        val direction = parseScrollDirection(command.direction) ?: return@mapNotNull null
                        val amount = command.amount ?: 100
                        Action.Scroll(nodeId, direction, amount)
                    }
                    "back" -> {
                        Action.Back
                    }
                    else -> null
                }
            } catch (e: Exception) {
                null // Skip invalid commands
            }
        }
    }
    
    private fun parseScrollDirection(direction: String?): ScrollDirection? {
        return when (direction?.uppercase()) {
            "UP" -> ScrollDirection.UP
            "DOWN" -> ScrollDirection.DOWN
            "LEFT" -> ScrollDirection.LEFT
            "RIGHT" -> ScrollDirection.RIGHT
            else -> null
        }
    }
}

