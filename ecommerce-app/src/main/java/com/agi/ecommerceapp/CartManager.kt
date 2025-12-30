package com.agi.ecommerceapp

import com.agi.ecommerceapp.models.Product

object CartManager {
    private val cartItems = mutableListOf<CartItem>()
    
    data class CartItem(
        val product: Product,
        var quantity: Int = 1
    )
    
    fun addToCart(product: Product, quantity: Int = 1) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cartItems.add(CartItem(product, quantity))
        }
    }
    
    fun removeFromCart(productId: String) {
        cartItems.removeAll { it.product.id == productId }
    }
    
    fun getCartItems(): List<CartItem> = cartItems.toList()
    
    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.product.price * it.quantity }
    }
    
    fun clearCart() {
        cartItems.clear()
    }
    
    fun getItemCount(): Int = cartItems.sumOf { it.quantity }
}

