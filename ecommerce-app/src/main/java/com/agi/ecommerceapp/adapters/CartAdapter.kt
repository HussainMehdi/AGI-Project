package com.agi.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.ecommerceapp.CartManager
import com.agi.ecommerceapp.R
import com.agi.ecommerceapp.models.Product
import java.text.NumberFormat

class CartAdapter(
    private val cartItems: List<CartManager.CartItem>,
    private val onItemChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.cartItemName)
        val priceText: TextView = view.findViewById(R.id.cartItemPrice)
        val quantityText: TextView = view.findViewById(R.id.cartItemQuantity)
        val removeButton: Button = view.findViewById(R.id.removeButton)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.nameText.text = item.product.name
        holder.priceText.text = NumberFormat.getCurrencyInstance().format(item.product.price)
        holder.quantityText.text = "Qty: ${item.quantity}"
        
        holder.removeButton.setOnClickListener {
            CartManager.removeFromCart(item.product.id)
            onItemChanged()
        }
    }
    
    override fun getItemCount() = cartItems.size
}

