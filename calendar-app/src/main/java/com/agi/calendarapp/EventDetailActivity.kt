package com.agi.calendarapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agi.calendarapp.data.SampleData
import com.agi.calendarapp.databinding.ActivityEventDetailBinding
import com.agi.calendarapp.models.Event
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var event: Event
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val eventId = intent.getStringExtra("event_id") ?: return finish()
        event = SampleData.events.find { it.id == eventId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Event Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.eventTitle.text = event.title
        binding.eventDescription.text = event.description
        binding.eventLocation.text = event.location
        binding.eventTime.text = event.time
        
        // Format date
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(event.date)
            binding.eventDate.text = outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            binding.eventDate.text = event.date
        }
        
        val hours = event.duration / 60
        val minutes = event.duration % 60
        binding.eventDuration.text = if (hours > 0) {
            "${hours}h ${minutes}min"
        } else {
            "${minutes}min"
        }
        
        binding.deleteButton.setOnClickListener {
            Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

