package com.agi.sampleapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.assistantsdk.AssistantSdk
import com.agi.assistantsdk.models.Action
import com.agi.sampleapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    
    private val sampleItems = (1..20).map { "Item $it" }
    private lateinit var adapter: SampleAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupClickListeners()
        
        // Bind this activity to the SDK
        AssistantSdk.bind(this)
    }
    
    private fun setupRecyclerView() {
        adapter = SampleAdapter(sampleItems)
        binding.sampleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.sampleRecyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.sampleButton.setOnClickListener {
            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
        }
        
        binding.captureButton.setOnClickListener {
            captureAndDisplaySnapshot()
        }
        
        // Add some test action buttons programmatically for demo
        // These would normally be in a separate debug UI, but we'll add them inline for simplicity
    }
    
    private fun captureAndDisplaySnapshot() {
        try {
            val snapshot = AssistantSdk.capture()
            val json = gson.toJson(snapshot)
            binding.snapshotTextView.text = json
            
            // Also log for debugging
            android.util.Log.d("AssistantSDK", "Captured ${snapshot.nodes.size} root nodes")
            
            // Test action: Try to click the button
            testActions(snapshot)
        } catch (e: Exception) {
            binding.snapshotTextView.text = "Error: ${e.message}\n${e.stackTraceToString()}"
            android.util.Log.e("AssistantSDK", "Capture failed", e)
        }
    }
    
    private fun testActions(snapshot: com.agi.assistantsdk.models.UiSnapshot) {
        // Find the sample button node and try clicking it
        val buttonNode = findNodeById(snapshot.nodes, "sampleButton")
        if (buttonNode != null) {
            val result = AssistantSdk.perform(Action.Click(buttonNode.id))
            Toast.makeText(
                this,
                "Click action result: ${result.success} - ${result.message ?: result.code}",
                Toast.LENGTH_SHORT
            ).show()
        }
        
        // Try to set text on EditText
        val editTextNode = findNodeById(snapshot.nodes, "sampleEditText")
        if (editTextNode != null) {
            val result = AssistantSdk.perform(Action.SetText(editTextNode.id, "Hello from SDK!"))
            android.util.Log.d("AssistantSDK", "SetText result: ${result.success} - ${result.message ?: result.code}")
        }
    }
    
    private fun findNodeById(nodes: List<com.agi.assistantsdk.models.UiNode>, id: String): com.agi.assistantsdk.models.UiNode? {
        for (node in nodes) {
            if (node.id.contains(id)) {
                return node
            }
            val found = findNodeById(node.children, id)
            if (found != null) {
                return found
            }
        }
        return null
    }
}

