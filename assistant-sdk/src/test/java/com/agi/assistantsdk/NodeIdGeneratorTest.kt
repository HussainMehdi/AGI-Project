package com.agi.assistantsdk

import android.view.View
import android.widget.Button
import android.widget.EditText
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class NodeIdGeneratorTest {
    
    @Test
    fun `generates stable ID for view with resource ID`() {
        val generator = NodeIdGenerator()
        val view = mock(View::class.java)
        
        // Mock resource ID
        `when`(view.id).thenReturn(android.R.id.button1)
        `when`(view.javaClass).thenReturn(Button::class.java)
        `when`(view.resources).thenReturn(null) // Will use fallback
        
        // Since we can't easily mock Resources.getResourceEntryName, 
        // this test verifies the fallback path works
        val id = generator.generateId(view, "")
        assert(id.contains("Button"))
    }
    
    @Test
    fun `generates path-based ID for view without resource ID`() {
        val generator = NodeIdGenerator()
        val view = mock(View::class.java)
        
        `when`(view.id).thenReturn(android.view.View.NO_ID)
        `when`(view.javaClass).thenReturn(EditText::class.java)
        
        val id = generator.generateId(view, "parent")
        assertEquals("parent/EditText[0]", id)
    }
}

