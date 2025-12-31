package com.agi.fooddeliveryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.fooddeliveryapp.R
import com.agi.fooddeliveryapp.models.Restaurant
import com.bumptech.glide.Glide
import android.widget.ImageView
import java.text.NumberFormat

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    
    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.restaurantName)
        val cuisineText: TextView = view.findViewById(R.id.restaurantCuisine)
        val ratingText: TextView = view.findViewById(R.id.restaurantRating)
        val deliveryTimeText: TextView = view.findViewById(R.id.deliveryTime)
        val deliveryFeeText: TextView = view.findViewById(R.id.deliveryFee)
        val restaurantImage: ImageView = view.findViewById(R.id.restaurantImage)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.nameText.text = restaurant.name
        holder.cuisineText.text = restaurant.cuisine
        holder.ratingText.text = "★ ${restaurant.rating}"
        holder.deliveryTimeText.text = "${restaurant.deliveryTime} min"
        holder.deliveryFeeText.text = "${NumberFormat.getCurrencyInstance().format(restaurant.deliveryFee)} delivery"
        
        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(restaurant.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(holder.restaurantImage)
        
        holder.itemView.setOnClickListener {
            onItemClick(restaurant)
        }
    }
    
    override fun getItemCount() = restaurants.size
}

