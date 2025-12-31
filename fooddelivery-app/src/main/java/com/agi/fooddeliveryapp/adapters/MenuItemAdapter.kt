package com.agi.fooddeliveryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.fooddeliveryapp.CartManager
import com.agi.fooddeliveryapp.R
import com.agi.fooddeliveryapp.models.MenuItem
import com.bumptech.glide.Glide
import java.text.NumberFormat

class MenuItemAdapter(
    private val menuItems: List<MenuItem>,
    private val onItemClick: ((MenuItem) -> Unit)? = null
) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {
    
    class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.menuItemName)
        val descriptionText: TextView = view.findViewById(R.id.menuItemDescription)
        val priceText: TextView = view.findViewById(R.id.menuItemPrice)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
        val menuItemImage: ImageView = view.findViewById(R.id.menuItemImage)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_item, parent, false)
        return MenuItemViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.nameText.text = menuItem.name
        holder.descriptionText.text = menuItem.description
        holder.priceText.text = NumberFormat.getCurrencyInstance().format(menuItem.price)
        
        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(menuItem.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(holder.menuItemImage)
        
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(menuItem)
        }
        
        holder.addToCartButton.setOnClickListener {
            CartManager.addToCart(menuItem)
            android.widget.Toast.makeText(
                holder.itemView.context,
                "Added ${menuItem.name} to cart",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun getItemCount() = menuItems.size
}

