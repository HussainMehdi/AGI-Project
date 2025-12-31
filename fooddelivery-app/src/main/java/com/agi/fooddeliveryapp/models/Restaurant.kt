package com.agi.fooddeliveryapp.models

data class Restaurant(
    val id: String,
    val name: String,
    val cuisine: String,
    val rating: Float,
    val deliveryTime: Int, // in minutes
    val deliveryFee: Double,
    val imageUrl: String,
    val address: String
)

