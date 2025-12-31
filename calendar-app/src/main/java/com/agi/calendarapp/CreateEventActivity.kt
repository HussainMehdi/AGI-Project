package com.agi.calendarapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agi.calendarapp.databinding.ActivityCreateEventBinding

class CreateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Create Event"
        
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val description = binding.descriptionEditText.text.toString().trim()
            val location = binding.locationEditText.text.toString().trim()
            val date = binding.dateEditText.text.toString().trim()
            val time = binding.timeEditText.text.toString().trim()
            val duration = binding.durationEditText.text.toString().trim()
            
            if (title.isEmpty() || date.isEmpty() || time.isEmpty() || duration.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

