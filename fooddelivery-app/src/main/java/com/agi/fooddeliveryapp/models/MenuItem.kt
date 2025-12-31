package com.agi.fooddeliveryapp.models

data class MenuItem(
    val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)

