package com.agi.marketplaceapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agi.marketplaceapp.data.SampleData
import com.agi.marketplaceapp.databinding.ActivityAppDetailBinding
import com.agi.marketplaceapp.models.App
import com.bumptech.glide.Glide
import java.text.NumberFormat

class AppDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppDetailBinding
    private lateinit var app: App
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val appId = intent.getStringExtra("app_id") ?: return finish()
        app = SampleData.apps.find { it.id == appId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = app.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.appName.text = app.name
        binding.appDeveloper.text = app.developer
        binding.appRating.text = "★ ${app.rating}"
        binding.appDownloads.text = app.downloads
        binding.appSize.text = app.size
        binding.appDescription.text = app.description
        
        binding.appPrice.text = if (app.price == 0.0) {
            "Free"
        } else {
            NumberFormat.getCurrencyInstance().format(app.price)
        }
        
        // Load icon using Glide
        Glide.with(this)
            .load(app.iconUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(binding.appIcon)
        
        binding.installButton.setOnClickListener {
            Toast.makeText(this, "Installing ${app.name}...", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

