package com.agi.sampleapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.assistantsdk.AssistantSdk
import com.agi.assistantsdk.models.Action
import com.agi.sampleapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    
    private val sampleItems = (1..20).map { "Item $it" }
    private lateinit var adapter: SampleAdapter
    
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
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
        
        binding.executePromptButton.setOnClickListener {
            executeNlpPrompt()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
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
//            Toast.makeText(
//                this,
//                "Click action result: ${result.success} - ${result.message ?: result.code}",
//                Toast.LENGTH_SHORT
//            ).show()
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
    
    private fun executeNlpPrompt() {
        val prompt = binding.nlpPromptEditText.text.toString().trim()
        if (prompt.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Show loading state
        binding.executePromptButton.isEnabled = false
        binding.promptResultTextView.visibility = View.VISIBLE
        binding.promptResultTextView.text = getString(R.string.executing_prompt)
        
        // Execute on background thread
        scope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    AssistantSdk.executePrompt(prompt)
                }
                
                // Update UI on main thread
                binding.executePromptButton.isEnabled = true
                
                if (result.success) {
                    val message = getString(R.string.prompt_success, result.executedActions.size)
                    binding.promptResultTextView.text = message
                    binding.promptResultTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark, theme))
                    
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    
                    // Log detailed results
                    android.util.Log.d("AssistantSDK", "Prompt execution successful: ${result.executedActions.size} actions")
                    result.executedActions.forEachIndexed { index, actionResult ->
                        android.util.Log.d("AssistantSDK", "Action $index: ${actionResult.code} - ${actionResult.message ?: "Success"}")
                    }
                } else {
                    val errorMessage = result.error ?: "Unknown error"
                    val message = getString(R.string.prompt_error, errorMessage)
                    binding.promptResultTextView.text = message
                    binding.promptResultTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark, theme))
                    
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    android.util.Log.e("AssistantSDK", "Prompt execution failed: $errorMessage")
                }
            } catch (e: Exception) {
                binding.executePromptButton.isEnabled = true
                val errorMessage = e.message ?: "Unknown error"
                val message = getString(R.string.prompt_error, errorMessage)
                binding.promptResultTextView.text = message
                binding.promptResultTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark, theme))
                
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                android.util.Log.e("AssistantSDK", "Prompt execution error", e)
            }
        }
    }
}

