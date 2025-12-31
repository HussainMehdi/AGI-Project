package com.agi.marketplaceapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.assistantsdk.AssistantSdk
import com.agi.marketplaceapp.adapters.AppAdapter
import com.agi.marketplaceapp.data.SampleData
import com.agi.marketplaceapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AppAdapter
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var selectedCategory = "All"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        AssistantSdk.bind(this)
        
        setupCategories()
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupCategories() {
        binding.categoriesLayout.removeAllViews()
        SampleData.categories.forEach { category ->
            val button = Button(this).apply {
                text = category
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 16, 0)
                }
                setPadding(24, 12, 24, 12)
                isAllCaps = false
                setOnClickListener {
                    selectedCategory = category
                    updateCategoryButtons()
                    filterApps(category)
                }
                id = View.generateViewId()
            }
            binding.categoriesLayout.addView(button)
        }
        updateCategoryButtons()
    }
    
    private fun updateCategoryButtons() {
        for (i in 0 until binding.categoriesLayout.childCount) {
            val button = binding.categoriesLayout.getChildAt(i) as Button
            val category = SampleData.categories[i]
            if (category == selectedCategory) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                button.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            }
        }
    }
    
    private fun setupRecyclerView() {
        adapter = AppAdapter(SampleData.apps) { app ->
            val intent = Intent(this, AppDetailActivity::class.java)
            intent.putExtra("app_id", app.id)
            startActivity(intent)
        }
        binding.appsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.appsRecyclerView.adapter = adapter
    }
    
    private fun filterApps(category: String) {
        val filteredApps = SampleData.getAppsByCategory(category)
        adapter = AppAdapter(filteredApps) { app ->
            val intent = Intent(this, AppDetailActivity::class.java)
            intent.putExtra("app_id", app.id)
            startActivity(intent)
        }
        binding.appsRecyclerView.adapter = adapter
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

