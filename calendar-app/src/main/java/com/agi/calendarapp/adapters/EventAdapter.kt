package com.agi.calendarapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.calendarapp.R
import com.agi.calendarapp.models.Event
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(
    private val events: List<Event>,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    
    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.eventTitle)
        val timeText: TextView = view.findViewById(R.id.eventTime)
        val locationText: TextView = view.findViewById(R.id.eventLocation)
        val descriptionText: TextView = view.findViewById(R.id.eventDescription)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleText.text = event.title
        holder.timeText.text = event.time
        holder.locationText.text = event.location
        holder.descriptionText.text = event.description
        
        holder.itemView.setOnClickListener {
            onItemClick(event)
        }
    }
    
    override fun getItemCount() = events.size
}

