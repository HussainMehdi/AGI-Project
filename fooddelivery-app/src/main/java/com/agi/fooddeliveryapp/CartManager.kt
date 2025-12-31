package com.agi.fooddeliveryapp

import com.agi.fooddeliveryapp.models.MenuItem

object CartManager {
    private val cartItems = mutableListOf<MenuItem>()
    
    fun addToCart(item: MenuItem) {
        cartItems.add(item)
    }
    
    fun removeFromCart(item: MenuItem) {
        cartItems.remove(item)
    }
    
    fun getCartItems(): List<MenuItem> = cartItems.toList()
    
    fun getTotal(): Double {
        return cartItems.sumOf { it.price }
    }
    
    fun clearCart() {
        cartItems.clear()
    }
    
    fun getCartSize(): Int = cartItems.size
}

