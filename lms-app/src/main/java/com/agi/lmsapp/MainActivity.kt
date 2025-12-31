package com.agi.lmsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.assistantsdk.AssistantSdk
import com.agi.lmsapp.adapters.CourseAdapter
import com.agi.lmsapp.data.SampleData
import com.agi.lmsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CourseAdapter
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        AssistantSdk.bind(this)
        
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        adapter = CourseAdapter(SampleData.courses) { course ->
            val intent = Intent(this, CourseDetailActivity::class.java)
            intent.putExtra("course_id", course.id)
            startActivity(intent)
        }
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.coursesRecyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.executePromptButton.setOnClickListener {
            executeNlpPrompt()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
    
    private fun executeNlpPrompt() {
        val prompt = binding.nlpPromptEditText.text.toString().trim()
        if (prompt.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show()
            return
        }
        
        binding.executePromptButton.isEnabled = false
        binding.promptResultTextView.visibility = View.VISIBLE
        binding.promptResultTextView.text = getString(R.string.executing_prompt)
        
        scope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    AssistantSdk.executePrompt(prompt)
                }
                
                binding.executePromptButton.isEnabled = true
                
                if (result.success) {
                    val message = getString(R.string.prompt_success, result.executedActions.size)
                    binding.promptResultTextView.text = message
                    binding.promptResultTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark, theme))
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = result.error ?: "Unknown error"
                    val message = getString(R.string.prompt_error, errorMessage)
                    binding.promptResultTextView.text = message
                    binding.promptResultTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark, theme))
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                binding.executePromptButton.isEnabled = true
                val message = getString(R.string.prompt_error, e.message ?: "Unknown error")
                binding.promptResultTextView.text = message
                binding.promptResultTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark, theme))
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

