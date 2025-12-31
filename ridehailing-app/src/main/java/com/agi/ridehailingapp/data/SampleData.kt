package com.agi.ridehailingapp.data

import com.agi.ridehailingapp.models.Driver
import com.agi.ridehailingapp.models.Trip

object SampleData {
    val drivers = listOf(
        Driver(id = "d1", name = "John Smith", carModel = "Toyota Camry", carColor = "Black", licensePlate = "ABC-1234", rating = 4.8f, totalRides = 1245, carType = "Economy", imageUrl = "https://images.unsplash.com/photo-1502877338535-766e1452684a?w=400&h=300&fit=crop", estimatedArrival = 5),
        Driver(id = "d2", name = "Sarah Johnson", carModel = "Honda Accord", carColor = "White", licensePlate = "XYZ-5678", rating = 4.9f, totalRides = 2100, carType = "Economy", imageUrl = "https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=400&h=300&fit=crop", estimatedArrival = 3),
        Driver(id = "d3", name = "Michael Chen", carModel = "Tesla Model 3", carColor = "Red", licensePlate = "TSL-9012", rating = 4.7f, totalRides = 890, carType = "Premium", imageUrl = "https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=400&h=300&fit=crop", estimatedArrival = 7),
        Driver(id = "d4", name = "Emily Davis", carModel = "BMW 5 Series", carColor = "Silver", licensePlate = "BMW-3456", rating = 4.9f, totalRides = 567, carType = "Premium", imageUrl = "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=400&h=300&fit=crop", estimatedArrival = 4),
        Driver(id = "d5", name = "David Wilson", carModel = "Mercedes-Benz S-Class", carColor = "Black", licensePlate = "MBZ-7890", rating = 5.0f, totalRides = 345, carType = "Luxury", imageUrl = "https://images.unsplash.com/photo-1617531653332-bd46c24f2068?w=400&h=300&fit=crop", estimatedArrival = 6),
        Driver(id = "d6", name = "Jessica Brown", carModel = "Nissan Altima", carColor = "Blue", licensePlate = "NSA-1234", rating = 4.6f, totalRides = 987, carType = "Economy", imageUrl = "https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?w=400&h=300&fit=crop", estimatedArrival = 8),
        Driver(id = "d7", name = "Robert Taylor", carModel = "Audi A6", carColor = "Gray", licensePlate = "AUD-5678", rating = 4.8f, totalRides = 432, carType = "Premium", imageUrl = "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=400&h=300&fit=crop", estimatedArrival = 5),
        Driver(id = "d8", name = "Amanda Martinez", carModel = "Lexus ES", carColor = "White", licensePlate = "LEX-9012", rating = 4.9f, totalRides = 678, carType = "Premium", imageUrl = "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=400&h=300&fit=crop", estimatedArrival = 4),
        Driver(id = "d9", name = "James Anderson", carModel = "Ford Fusion", carColor = "Red", licensePlate = "FRD-3456", rating = 4.5f, totalRides = 1123, carType = "Economy", imageUrl = "https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?w=400&h=300&fit=crop", estimatedArrival = 6),
        Driver(id = "d10", name = "Lisa Garcia", carModel = "Porsche Panamera", carColor = "Black", licensePlate = "POR-7890", rating = 5.0f, totalRides = 234, carType = "Luxury", imageUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=400&h=300&fit=crop", estimatedArrival = 10),
        Driver(id = "d11", name = "Christopher Lee", carModel = "Hyundai Sonata", carColor = "Silver", licensePlate = "HYD-1234", rating = 4.7f, totalRides = 1456, carType = "Economy", imageUrl = "https://images.unsplash.com/photo-1502877338535-766e1452684a?w=400&h=300&fit=crop", estimatedArrival = 7),
        Driver(id = "d12", name = "Michelle White", carModel = "Volvo XC90", carColor = "Blue", licensePlate = "VOL-5678", rating = 4.8f, totalRides = 789, carType = "Premium", imageUrl = "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=400&h=300&fit=crop", estimatedArrival = 5)
    )
    
    val trips = listOf(
        Trip(id = "t1", driverId = "d2", driverName = "Sarah Johnson", pickupLocation = "123 Main St", dropoffLocation = "Airport Terminal 1", date = "2024-12-28", time = "14:30", fare = 45.50, status = "Completed", carModel = "Honda Accord"),
        Trip(id = "t2", driverId = "d4", driverName = "Emily Davis", pickupLocation = "456 Oak Ave", dropoffLocation = "Downtown Mall", date = "2024-12-27", time = "10:15", fare = 22.75, status = "Completed", carModel = "BMW 5 Series"),
        Trip(id = "t3", driverId = "d5", driverName = "David Wilson", pickupLocation = "789 Pine Rd", dropoffLocation = "City Center", date = "2024-12-26", time = "18:45", fare = 38.00, status = "Completed", carModel = "Mercedes-Benz S-Class"),
        Trip(id = "t4", driverId = "d1", driverName = "John Smith", pickupLocation = "321 Elm St", dropoffLocation = "Shopping Plaza", date = "2024-12-25", time = "12:00", fare = 18.50, status = "Completed", carModel = "Toyota Camry"),
        Trip(id = "t5", driverId = "d7", driverName = "Robert Taylor", pickupLocation = "654 Maple Dr", dropoffLocation = "University Campus", date = "2024-12-24", time = "09:30", fare = 28.25, status = "Completed", carModel = "Audi A6"),
        Trip(id = "t6", driverId = "d8", driverName = "Amanda Martinez", pickupLocation = "987 Cedar Ln", dropoffLocation = "Train Station", date = "2024-12-23", time = "16:20", fare = 32.00, status = "Completed", carModel = "Lexus ES"),
        Trip(id = "t7", driverId = "d3", driverName = "Michael Chen", pickupLocation = "147 Birch Ave", dropoffLocation = "Hospital", date = "2024-12-22", time = "11:45", fare = 26.75, status = "Completed", carModel = "Tesla Model 3"),
        Trip(id = "t8", driverId = "d6", driverName = "Jessica Brown", pickupLocation = "258 Spruce St", dropoffLocation = "Convention Center", date = "2024-12-21", time = "15:10", fare = 35.50, status = "Completed", carModel = "Nissan Altima")
    )
    
    fun getDriversByType(carType: String): List<Driver> {
        return if (carType == "All") {
            drivers
        } else {
            drivers.filter { it.carType == carType }
        }
    }
}

