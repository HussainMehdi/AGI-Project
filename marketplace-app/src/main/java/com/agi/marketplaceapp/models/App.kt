package com.agi.marketplaceapp.models

data class App(
    val id: String,
    val name: String,
    val developer: String,
    val category: String,
    val rating: Float,
    val downloads: String,
    val description: String,
    val price: Double,
    val size: String,
    val iconUrl: String
)

