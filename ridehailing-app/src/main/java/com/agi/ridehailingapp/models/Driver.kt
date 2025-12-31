package com.agi.ridehailingapp.models

data class Driver(
    val id: String,
    val name: String,
    val carModel: String,
    val carColor: String,
    val licensePlate: String,
    val rating: Float,
    val totalRides: Int,
    val carType: String, // Economy, Premium, Luxury
    val imageUrl: String,
    val estimatedArrival: Int // in minutes
)

