package com.agi.fooddeliveryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.fooddeliveryapp.CartManager
import com.agi.fooddeliveryapp.R
import com.agi.fooddeliveryapp.models.MenuItem
import java.text.NumberFormat

class CartAdapter(
    private val cartItems: List<MenuItem>,
    private val onRemoveClick: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.cartItemName)
        val itemPrice: TextView = view.findViewById(R.id.cartItemPrice)
        val removeButton: Button = view.findViewById(R.id.removeButton)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = NumberFormat.getCurrencyInstance().format(item.price)
        
        holder.removeButton.setOnClickListener {
            CartManager.removeFromCart(item)
            onRemoveClick()
        }
    }
    
    override fun getItemCount() = cartItems.size
}

