package com.agi.calendarapp.models

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String, // yyyy-MM-dd
    val time: String, // HH:mm
    val location: String,
    val duration: Int // in minutes
)

