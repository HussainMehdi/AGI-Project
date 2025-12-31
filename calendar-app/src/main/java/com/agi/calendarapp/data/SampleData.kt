package com.agi.calendarapp.data

import com.agi.calendarapp.models.Event

object SampleData {
    val events = listOf(
        Event(id = "e1", title = "Team Meeting", description = "Weekly team sync meeting to discuss project progress", date = "2024-12-31", time = "10:00", location = "Conference Room A", duration = 60),
        Event(id = "e2", title = "Lunch with Client", description = "Business lunch with ABC Corp", date = "2024-12-31", time = "12:30", location = "Restaurant Downtown", duration = 90),
        Event(id = "e3", title = "Doctor Appointment", description = "Annual health checkup", date = "2025-01-02", time = "14:00", location = "City Medical Center", duration = 30),
        Event(id = "e4", title = "Project Presentation", description = "Present Q4 results to stakeholders", date = "2025-01-03", time = "09:00", location = "Main Auditorium", duration = 120),
        Event(id = "e5", title = "Gym Session", description = "Personal training session", date = "2025-01-03", time = "18:00", location = "Fitness Center", duration = 60),
        Event(id = "e6", title = "Coffee with Friend", description = "Catch up with Sarah", date = "2025-01-04", time = "15:30", location = "Starbucks", duration = 45),
        Event(id = "e7", title = "Conference Call", description = "International team call", date = "2025-01-05", time = "11:00", location = "Virtual", duration = 45),
        Event(id = "e8", title = "Birthday Party", description = "John's 30th birthday celebration", date = "2025-01-05", time = "19:00", location = "City Park", duration = 180),
        Event(id = "e9", title = "Workshop", description = "Android development workshop", date = "2025-01-06", time = "13:00", location = "Tech Hub", duration = 240),
        Event(id = "e10", title = "Movie Night", description = "Watch latest release", date = "2025-01-07", time = "20:00", location = "Cinema Complex", duration = 150),
        Event(id = "e11", title = "Interview", description = "Job interview at Tech Company", date = "2025-01-08", time = "10:30", location = "Tech Tower", duration = 60),
        Event(id = "e12", title = "Dinner Date", description = "Anniversary dinner", date = "2025-01-08", time = "19:30", location = "Fine Dining Restaurant", duration = 120),
        Event(id = "e13", title = "Haircut", description = "Haircut appointment", date = "2025-01-09", time = "16:00", location = "Salon Downtown", duration = 30),
        Event(id = "e14", title = "Yoga Class", description = "Evening yoga session", date = "2025-01-09", time = "18:30", location = "Yoga Studio", duration = 60),
        Event(id = "e15", title = "Shopping", description = "Weekly grocery shopping", date = "2025-01-10", time = "14:00", location = "Supermarket", duration = 60),
        Event(id = "e16", title = "Book Club", description = "Monthly book discussion", date = "2025-01-11", time = "17:00", location = "Library", duration = 90),
        Event(id = "e17", title = "Concert", description = "Live music performance", date = "2025-01-12", time = "20:00", location = "Concert Hall", duration = 120),
        Event(id = "e18", title = "Business Trip", description = "Flight to New York", date = "2025-01-13", time = "08:00", location = "Airport", duration = 480),
        Event(id = "e19", title = "Parent-Teacher Meeting", description = "School meeting", date = "2025-01-14", time = "15:00", location = "Elementary School", duration = 30),
        Event(id = "e20", title = "Weekend Getaway", description = "Road trip to beach", date = "2025-01-15", time = "09:00", location = "Beach Resort", duration = 1440)
    )
    
    fun getEventsByDate(date: String): List<Event> {
        return events.filter { it.date == date }
    }
    
    fun getAllEventDates(): List<String> {
        return events.map { it.date }.distinct().sorted()
    }
}

