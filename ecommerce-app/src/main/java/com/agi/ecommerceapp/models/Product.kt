package com.agi.ecommerceapp.models

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val category: String,
    val rating: Float,
    val reviews: Int,
    val imageUrl: String
)

