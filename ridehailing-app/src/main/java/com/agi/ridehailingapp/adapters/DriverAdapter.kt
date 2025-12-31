package com.agi.ridehailingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.ridehailingapp.R
import com.agi.ridehailingapp.models.Driver
import com.bumptech.glide.Glide
import android.widget.ImageView

class DriverAdapter(
    private val drivers: List<Driver>,
    private val onItemClick: (Driver) -> Unit
) : RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {
    
    class DriverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.driverName)
        val carModelText: TextView = view.findViewById(R.id.carModel)
        val ratingText: TextView = view.findViewById(R.id.driverRating)
        val carTypeText: TextView = view.findViewById(R.id.carType)
        val estimatedArrivalText: TextView = view.findViewById(R.id.estimatedArrival)
        val carImage: ImageView = view.findViewById(R.id.driverCarImage)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_driver, parent, false)
        return DriverViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driver = drivers[position]
        holder.nameText.text = driver.name
        holder.carModelText.text = "${driver.carColor} ${driver.carModel} • ${driver.licensePlate}"
        holder.ratingText.text = "★ ${driver.rating}"
        holder.carTypeText.text = driver.carType
        holder.estimatedArrivalText.text = "${driver.estimatedArrival} min"
        
        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(driver.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(holder.carImage)
        
        holder.itemView.setOnClickListener {
            onItemClick(driver)
        }
    }
    
    override fun getItemCount() = drivers.size
}

