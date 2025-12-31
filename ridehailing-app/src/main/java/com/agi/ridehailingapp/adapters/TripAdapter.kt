package com.agi.ridehailingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.ridehailingapp.R
import com.agi.ridehailingapp.models.Trip
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class TripAdapter(
    private val trips: List<Trip>
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {
    
    class TripViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.tripDate)
        val timeText: TextView = view.findViewById(R.id.tripTime)
        val fareText: TextView = view.findViewById(R.id.tripFare)
        val driverNameText: TextView = view.findViewById(R.id.driverName)
        val carModelText: TextView = view.findViewById(R.id.carModel)
        val pickupLocationText: TextView = view.findViewById(R.id.pickupLocation)
        val dropoffLocationText: TextView = view.findViewById(R.id.dropoffLocation)
        val statusText: TextView = view.findViewById(R.id.tripStatus)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        
        // Format date
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(trip.date)
            holder.dateText.text = outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            holder.dateText.text = trip.date
        }
        
        holder.timeText.text = trip.time
        holder.fareText.text = NumberFormat.getCurrencyInstance().format(trip.fare)
        holder.driverNameText.text = trip.driverName
        holder.carModelText.text = trip.carModel
        holder.pickupLocationText.text = "From: ${trip.pickupLocation}"
        holder.dropoffLocationText.text = "To: ${trip.dropoffLocation}"
        holder.statusText.text = trip.status
    }
    
    override fun getItemCount() = trips.size
}

