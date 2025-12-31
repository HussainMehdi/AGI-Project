package com.agi.ridehailingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agi.ridehailingapp.data.SampleData
import com.agi.ridehailingapp.databinding.ActivityBookingBinding
import com.agi.ridehailingapp.models.Driver
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Random

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var driver: Driver
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val driverId = intent.getStringExtra("driver_id") ?: return finish()
        driver = SampleData.drivers.find { it.id == driverId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Book Ride"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.driverName.text = driver.name
        binding.carModel.text = "${driver.carColor} ${driver.carModel} • ${driver.licensePlate}"
        binding.driverRating.text = "★ ${driver.rating}"
        
        // Calculate estimated fare (random between 15-50)
        val estimatedFare = 15.0 + Random().nextDouble() * 35.0
        binding.estimatedFare.text = "Estimated Fare: ${NumberFormat.getCurrencyInstance().format(estimatedFare)}"
        
        // Load image using Glide
        Glide.with(this)
            .load(driver.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(binding.driverCarImage)
        
        binding.confirmBookingButton.setOnClickListener {
            val pickup = binding.pickupLocationEditText.text.toString().trim()
            val dropoff = binding.dropoffLocationEditText.text.toString().trim()
            
            if (pickup.isEmpty() || dropoff.isEmpty()) {
                Toast.makeText(this, "Please enter both pickup and dropoff locations", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            Toast.makeText(this, "Ride booked successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

