package com.agi.marketplaceapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.marketplaceapp.R
import com.agi.marketplaceapp.models.App
import com.bumptech.glide.Glide
import java.text.NumberFormat

class AppAdapter(
    private val apps: List<App>,
    private val onItemClick: (App) -> Unit
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
    
    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.appName)
        val developerText: TextView = view.findViewById(R.id.appDeveloper)
        val ratingText: TextView = view.findViewById(R.id.appRating)
        val downloadsText: TextView = view.findViewById(R.id.appDownloads)
        val installButton: Button = view.findViewById(R.id.installButton)
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.nameText.text = app.name
        holder.developerText.text = app.developer
        holder.ratingText.text = "★ ${app.rating}"
        holder.downloadsText.text = app.downloads
        
        // Load icon using Glide
        Glide.with(holder.itemView.context)
            .load(app.iconUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(holder.appIcon)
        
        holder.itemView.setOnClickListener {
            onItemClick(app)
        }
        
        holder.installButton.setOnClickListener {
            android.widget.Toast.makeText(
                holder.itemView.context,
                "Installing ${app.name}...",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun getItemCount() = apps.size
}

