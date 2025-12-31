package com.agi.ridehailingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.ridehailingapp.adapters.TripAdapter
import com.agi.ridehailingapp.data.SampleData
import com.agi.ridehailingapp.databinding.ActivityTripHistoryBinding

class TripHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTripHistoryBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Trip History"
        
        val adapter = TripAdapter(SampleData.trips)
        binding.tripsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.tripsRecyclerView.adapter = adapter
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

