package com.agi.assistantsdk

import android.text.InputType
import android.view.View
import android.widget.EditText
import com.agi.assistantsdk.models.NodeState
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*

class MaskingUtilsTest {
    
    @Test
    fun `masks password EditText by default`() {
        val config = AssistantSdkConfig.default()
        val editText = mock(EditText::class.java)
        
        `when`(editText.inputType).thenReturn(
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        )
        
        val isSensitive = MaskingUtils.isSensitive(editText, config)
        assertTrue("Password field should be marked sensitive", isSensitive)
    }
    
    @Test
    fun `does not mask regular EditText by default`() {
        val config = AssistantSdkConfig.default()
        val editText = mock(EditText::class.java)
        
        `when`(editText.inputType).thenReturn(InputType.TYPE_CLASS_TEXT)
        
        val isSensitive = MaskingUtils.isSensitive(editText, config)
        assertFalse("Regular text field should not be marked sensitive", isSensitive)
    }
    
    @Test
    fun `masks view with sensitive view ID`() {
        val config = AssistantSdkConfig(
            sensitiveViewIds = setOf("passwordField")
        )
        val view = mock(View::class.java)
        val resources = mock(android.content.res.Resources::class.java)
        
        `when`(view.id).thenReturn(123)
        `when`(view.resources).thenReturn(resources)
        `when`(resources.getResourceEntryName(123)).thenReturn("passwordField")
        
        val isSensitive = MaskingUtils.isSensitive(view, config)
        assertTrue("View with sensitive ID should be marked sensitive", isSensitive)
    }
    
    @Test
    fun `masks text when sensitive`() {
        val masked = MaskingUtils.maskText("password123", true)
        assertEquals("***", masked)
    }
    
    @Test
    fun `does not mask text when not sensitive`() {
        val original = "hello world"
        val masked = MaskingUtils.maskText(original, false)
        assertEquals(original, masked)
    }
    
    @Test
    fun `creates NodeState with sensitive flag`() {
        val editText = mock(EditText::class.java)
        `when`(editText.inputType).thenReturn(
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        )
        
        val config = AssistantSdkConfig.default()
        val state = MaskingUtils.createNodeState(
            editText, config, enabled = true, clickable = false,
            focusable = true, focused = false, selected = false, checked = false
        )
        
        assertTrue("NodeState should have sensitive flag set", state.sensitive)
        assertTrue("NodeState should preserve enabled flag", state.enabled)
        assertTrue("NodeState should preserve focusable flag", state.focusable)
    }
}

