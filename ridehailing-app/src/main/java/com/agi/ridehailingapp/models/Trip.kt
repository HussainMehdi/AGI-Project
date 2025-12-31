package com.agi.ridehailingapp.models

data class Trip(
    val id: String,
    val driverId: String,
    val driverName: String,
    val pickupLocation: String,
    val dropoffLocation: String,
    val date: String,
    val time: String,
    val fare: Double,
    val status: String, // Completed, Cancelled, In Progress
    val carModel: String
)

