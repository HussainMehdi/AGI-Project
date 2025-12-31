package com.agi.fooddeliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.fooddeliveryapp.adapters.MenuItemAdapter
import com.agi.fooddeliveryapp.data.SampleData
import com.agi.fooddeliveryapp.databinding.ActivityRestaurantDetailBinding
import com.agi.fooddeliveryapp.models.Restaurant
import com.bumptech.glide.Glide
import java.text.NumberFormat

class RestaurantDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding
    private lateinit var restaurant: Restaurant
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val restaurantId = intent.getStringExtra("restaurant_id") ?: return finish()
        restaurant = SampleData.restaurants.find { it.id == restaurantId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = restaurant.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.restaurantName.text = restaurant.name
        binding.restaurantCuisine.text = restaurant.cuisine
        binding.restaurantRating.text = "★ ${restaurant.rating}"
        binding.deliveryTime.text = "${restaurant.deliveryTime} min"
        binding.deliveryFee.text = "${NumberFormat.getCurrencyInstance().format(restaurant.deliveryFee)} delivery fee"
        binding.restaurantAddress.text = restaurant.address
        
        // Load restaurant image using Glide
        Glide.with(this)
            .load(restaurant.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(binding.restaurantImage)
        
        val menuItems = SampleData.getMenuItemsByRestaurant(restaurant.id)
        val adapter = MenuItemAdapter(menuItems)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

