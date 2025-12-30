package com.agi.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.ecommerceapp.CartManager
import com.agi.ecommerceapp.R
import com.agi.ecommerceapp.models.Product
import com.bumptech.glide.Glide
import java.text.NumberFormat

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.productName)
        val priceText: TextView = view.findViewById(R.id.productPrice)
        val ratingText: TextView = view.findViewById(R.id.productRating)
        val reviewsText: TextView = view.findViewById(R.id.productReviews)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
        val productImage: ImageView = view.findViewById(R.id.productImage)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.nameText.text = product.name
        holder.priceText.text = NumberFormat.getCurrencyInstance().format(product.price)
        holder.ratingText.text = "★ ${product.rating}"
        holder.reviewsText.text = "(${product.reviews})"
        
        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .centerCrop()
            .into(holder.productImage)
        
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
        
        holder.addToCartButton.setOnClickListener {
            CartManager.addToCart(product)
            android.widget.Toast.makeText(
                holder.itemView.context,
                "Added ${product.name} to cart",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun getItemCount() = products.size
}

