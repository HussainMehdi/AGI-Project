package com.agi.ridehailingapp

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
import com.agi.ridehailingapp.adapters.DriverAdapter
import com.agi.ridehailingapp.data.SampleData
import com.agi.ridehailingapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DriverAdapter
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var selectedCarType = "All"
    private val carTypes = listOf("All", "Economy", "Premium", "Luxury")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        AssistantSdk.bind(this)
        
        setupCarTypes()
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupCarTypes() {
        binding.carTypesLayout.removeAllViews()
        carTypes.forEach { carType ->
            val button = Button(this).apply {
                text = carType
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 16, 0)
                }
                setPadding(24, 12, 24, 12)
                isAllCaps = false
                setOnClickListener {
                    selectedCarType = carType
                    updateCarTypeButtons()
                    filterDrivers(carType)
                }
                id = View.generateViewId()
            }
            binding.carTypesLayout.addView(button)
        }
        updateCarTypeButtons()
    }
    
    private fun updateCarTypeButtons() {
        for (i in 0 until binding.carTypesLayout.childCount) {
            val button = binding.carTypesLayout.getChildAt(i) as Button
            val carType = carTypes[i]
            if (carType == selectedCarType) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                button.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            }
        }
    }
    
    private fun setupRecyclerView() {
        adapter = DriverAdapter(SampleData.drivers) { driver ->
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("driver_id", driver.id)
            startActivity(intent)
        }
        binding.driversRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.driversRecyclerView.adapter = adapter
    }
    
    private fun filterDrivers(carType: String) {
        val filteredDrivers = SampleData.getDriversByType(carType)
        adapter = DriverAdapter(filteredDrivers) { driver ->
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("driver_id", driver.id)
            startActivity(intent)
        }
        binding.driversRecyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.tripHistoryFab.setOnClickListener {
            startActivity(Intent(this, TripHistoryActivity::class.java))
        }
        
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

