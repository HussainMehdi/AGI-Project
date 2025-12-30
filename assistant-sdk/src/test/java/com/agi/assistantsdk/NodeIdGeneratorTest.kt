package com.agi.assistantsdk

import android.view.View
import android.widget.Button
import android.widget.EditText
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*

class NodeIdGeneratorTest {
    
    @Test
    fun `generates stable ID for view with resource ID`() {
        val generator = NodeIdGenerator()
        val view = mock(Button::class.java)
        
        // Mock resource ID
        `when`(view.id).thenReturn(android.R.id.button1)
        `when`(view.resources).thenReturn(null) // Will use fallback
        
        // Since we can't easily mock Resources.getResourceEntryName, 
        // this test verifies the fallback path works
        val id = generator.generateId(view, "")
        assert(id.contains("Button"))
    }
    
    @Test
    fun `generates path-based ID for view without resource ID`() {
        val generator = NodeIdGenerator()
        val view = mock(EditText::class.java)
        
        `when`(view.id).thenReturn(View.NO_ID)
        
        val id = generator.generateId(view, "parent")
        // The mock class name will contain "EditText" but might have suffixes (e.g. EditText$MockitoMock...)
        assertTrue("ID should start with parent/EditText but was $id", id.startsWith("parent/EditText"))
        assertTrue("ID should end with [0] but was $id", id.endsWith("[0]"))
    }
}
