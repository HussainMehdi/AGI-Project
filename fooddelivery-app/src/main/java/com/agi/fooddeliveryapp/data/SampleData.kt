package com.agi.fooddeliveryapp.data

import com.agi.fooddeliveryapp.models.MenuItem
import com.agi.fooddeliveryapp.models.Restaurant

object SampleData {
    val categories = listOf("All", "Pizza", "Burger", "Asian", "Mexican", "Italian", "Desserts")
    
    val restaurants = listOf(
        Restaurant(id = "r1", name = "Domino's Pizza", cuisine = "Pizza", rating = 4.5f, deliveryTime = 30, deliveryFee = 2.99, imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400&h=300&fit=crop", address = "123 Main St"),
        Restaurant(id = "r2", name = "McDonald's", cuisine = "Burger", rating = 4.3f, deliveryTime = 25, deliveryFee = 1.99, imageUrl = "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=400&h=300&fit=crop", address = "456 Oak Ave"),
        Restaurant(id = "r3", name = "Panda Express", cuisine = "Asian", rating = 4.6f, deliveryTime = 35, deliveryFee = 3.49, imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400&h=300&fit=crop", address = "789 Pine Rd"),
        Restaurant(id = "r4", name = "Taco Bell", cuisine = "Mexican", rating = 4.2f, deliveryTime = 20, deliveryFee = 1.99, imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=300&fit=crop", address = "321 Elm St"),
        Restaurant(id = "r5", name = "Olive Garden", cuisine = "Italian", rating = 4.7f, deliveryTime = 40, deliveryFee = 4.99, imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400&h=300&fit=crop", address = "654 Maple Dr"),
        Restaurant(id = "r6", name = "Papa John's", cuisine = "Pizza", rating = 4.4f, deliveryTime = 30, deliveryFee = 2.99, imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=300&fit=crop", address = "987 Cedar Ln"),
        Restaurant(id = "r7", name = "Burger King", cuisine = "Burger", rating = 4.1f, deliveryTime = 25, deliveryFee = 1.99, imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=300&fit=crop", address = "147 Birch Ave"),
        Restaurant(id = "r8", name = "Subway", cuisine = "Burger", rating = 4.0f, deliveryTime = 20, deliveryFee = 1.49, imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349?w=400&h=300&fit=crop", address = "258 Spruce St"),
        Restaurant(id = "r9", name = "KFC", cuisine = "Burger", rating = 4.3f, deliveryTime = 30, deliveryFee = 2.99, imageUrl = "https://images.unsplash.com/photo-1527474305487-b87b222841cc?w=400&h=300&fit=crop", address = "369 Willow Rd"),
        Restaurant(id = "r10", name = "Pizza Hut", cuisine = "Pizza", rating = 4.5f, deliveryTime = 35, deliveryFee = 3.49, imageUrl = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400&h=300&fit=crop", address = "741 Cherry Blvd"),
        Restaurant(id = "r11", name = "Sushi House", cuisine = "Asian", rating = 4.8f, deliveryTime = 45, deliveryFee = 4.99, imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400&h=300&fit=crop", address = "852 Ash Way"),
        Restaurant(id = "r12", name = "Chipotle", cuisine = "Mexican", rating = 4.6f, deliveryTime = 30, deliveryFee = 2.99, imageUrl = "https://images.unsplash.com/photo-1565299585323-38174c40bc50?w=400&h=300&fit=crop", address = "963 Poplar St"),
        Restaurant(id = "r13", name = "Starbucks", cuisine = "Desserts", rating = 4.7f, deliveryTime = 20, deliveryFee = 1.99, imageUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=300&fit=crop", address = "159 Coffee Ave"),
        Restaurant(id = "r14", name = "Baskin-Robbins", cuisine = "Desserts", rating = 4.4f, deliveryTime = 25, deliveryFee = 2.49, imageUrl = "https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400&h=300&fit=crop", address = "357 Ice Cream Ln"),
        Restaurant(id = "r15", name = "Little Caesars", cuisine = "Pizza", rating = 4.2f, deliveryTime = 25, deliveryFee = 1.99, imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400&h=300&fit=crop", address = "468 Pizza St")
    )
    
    val menuItems = listOf(
        // Domino's Pizza
        MenuItem(id = "m1", restaurantId = "r1", name = "Pepperoni Pizza", description = "Classic pepperoni with mozzarella cheese", price = 12.99, category = "Pizza", imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=300&h=300&fit=crop"),
        MenuItem(id = "m2", restaurantId = "r1", name = "Margherita Pizza", description = "Fresh mozzarella, tomato sauce, basil", price = 11.99, category = "Pizza", imageUrl = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=300&h=300&fit=crop"),
        MenuItem(id = "m3", restaurantId = "r1", name = "Chicken BBQ Pizza", description = "Grilled chicken, BBQ sauce, onions", price = 14.99, category = "Pizza", imageUrl = "https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?w=300&h=300&fit=crop"),
        MenuItem(id = "m4", restaurantId = "r1", name = "Garlic Bread", description = "Crispy garlic bread with herbs", price = 5.99, category = "Sides", imageUrl = "https://images.unsplash.com/photo-1572441713132-51c75654db73?w=300&h=300&fit=crop"),
        MenuItem(id = "m5", restaurantId = "r1", name = "Caesar Salad", description = "Fresh romaine lettuce with Caesar dressing", price = 8.99, category = "Salads", imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=300&h=300&fit=crop"),
        
        // McDonald's
        MenuItem(id = "m6", restaurantId = "r2", name = "Big Mac", description = "Two beef patties, special sauce, lettuce, cheese", price = 5.99, category = "Burger", imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300&h=300&fit=crop"),
        MenuItem(id = "m7", restaurantId = "r2", name = "Quarter Pounder", description = "Quarter pound beef patty with cheese", price = 6.49, category = "Burger", imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349?w=300&h=300&fit=crop"),
        MenuItem(id = "m8", restaurantId = "r2", name = "Chicken McNuggets", description = "10 pieces of crispy chicken nuggets", price = 5.49, category = "Chicken", imageUrl = "https://images.unsplash.com/photo-1527474305487-b87b222841cc?w=300&h=300&fit=crop"),
        MenuItem(id = "m9", restaurantId = "r2", name = "French Fries", description = "Crispy golden fries", price = 3.49, category = "Sides", imageUrl = "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=300&h=300&fit=crop"),
        MenuItem(id = "m10", restaurantId = "r2", name = "McFlurry", description = "Vanilla soft serve with M&M's", price = 4.99, category = "Desserts", imageUrl = "https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=300&h=300&fit=crop"),
        
        // Panda Express
        MenuItem(id = "m11", restaurantId = "r3", name = "Orange Chicken", description = "Crispy chicken in tangy orange sauce", price = 9.99, category = "Asian", imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=300&h=300&fit=crop"),
        MenuItem(id = "m12", restaurantId = "r3", name = "Kung Pao Chicken", description = "Spicy chicken with peanuts and vegetables", price = 9.99, category = "Asian", imageUrl = "https://images.unsplash.com/photo-1563379091339-03246963d19c?w=300&h=300&fit=crop"),
        MenuItem(id = "m13", restaurantId = "r3", name = "Beef and Broccoli", description = "Tender beef with fresh broccoli", price = 10.99, category = "Asian", imageUrl = "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=300&h=300&fit=crop"),
        MenuItem(id = "m14", restaurantId = "r3", name = "Chow Mein", description = "Stir-fried noodles with vegetables", price = 7.99, category = "Asian", imageUrl = "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=300&h=300&fit=crop"),
        MenuItem(id = "m15", restaurantId = "r3", name = "Fried Rice", description = "Steamed rice stir-fried with eggs and vegetables", price = 7.99, category = "Asian", imageUrl = "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=300&h=300&fit=crop"),
        
        // Taco Bell
        MenuItem(id = "m16", restaurantId = "r4", name = "Crunchwrap Supreme", description = "Beef, nacho cheese, sour cream, lettuce", price = 6.99, category = "Mexican", imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=300&h=300&fit=crop"),
        MenuItem(id = "m17", restaurantId = "r4", name = "Chalupa Supreme", description = "Fried shell with beef, cheese, lettuce, tomato", price = 5.99, category = "Mexican", imageUrl = "https://images.unsplash.com/photo-1565299585323-38174c40bc50?w=300&h=300&fit=crop"),
        MenuItem(id = "m18", restaurantId = "r4", name = "Nachos BellGrande", description = "Nachos with beef, cheese, sour cream, tomatoes", price = 7.99, category = "Mexican", imageUrl = "https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=300&h=300&fit=crop"),
        MenuItem(id = "m19", restaurantId = "r4", name = "Quesadilla", description = "Grilled tortilla with cheese and chicken", price = 4.99, category = "Mexican", imageUrl = "https://images.unsplash.com/photo-1618040996337-56904b7850b9?w=300&h=300&fit=crop"),
        MenuItem(id = "m20", restaurantId = "r4", name = "Cinnamon Twists", description = "Sweet cinnamon twists", price = 2.99, category = "Desserts", imageUrl = "https://images.unsplash.com/photo-1551024506-0bccd828d307?w=300&h=300&fit=crop"),
        
        // Olive Garden
        MenuItem(id = "m21", restaurantId = "r5", name = "Fettuccine Alfredo", description = "Creamy Alfredo sauce with fettuccine", price = 16.99, category = "Italian", imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=300&h=300&fit=crop"),
        MenuItem(id = "m22", restaurantId = "r5", name = "Chicken Parmigiana", description = "Breaded chicken with marinara and mozzarella", price = 18.99, category = "Italian", imageUrl = "https://images.unsplash.com/photo-1527477396000-e27137b33bfb?w=300&h=300&fit=crop"),
        MenuItem(id = "m23", restaurantId = "r5", name = "Lasagna", description = "Layered pasta with meat and cheese", price = 17.99, category = "Italian", imageUrl = "https://images.unsplash.com/photo-1574894709920-11b28e7367e3?w=300&h=300&fit=crop"),
        MenuItem(id = "m24", restaurantId = "r5", name = "Garlic Breadsticks", description = "Warm breadsticks with garlic butter", price = 6.99, category = "Sides", imageUrl = "https://images.unsplash.com/photo-1572441713132-51c75654db73?w=300&h=300&fit=crop"),
        MenuItem(id = "m25", restaurantId = "r5", name = "Tiramisu", description = "Classic Italian dessert", price = 8.99, category = "Desserts", imageUrl = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=300&h=300&fit=crop"),
        
        // More items for variety
        MenuItem(id = "m26", restaurantId = "r6", name = "Hawaiian Pizza", description = "Ham and pineapple on pizza", price = 13.99, category = "Pizza", imageUrl = "https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?w=300&h=300&fit=crop"),
        MenuItem(id = "m27", restaurantId = "r7", name = "Whopper", description = "Flame-grilled beef burger", price = 6.99, category = "Burger", imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300&h=300&fit=crop"),
        MenuItem(id = "m28", restaurantId = "r11", name = "Salmon Sushi Roll", description = "Fresh salmon sushi roll", price = 12.99, category = "Asian", imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=300&h=300&fit=crop"),
        MenuItem(id = "m29", restaurantId = "r12", name = "Burrito Bowl", description = "Rice, beans, meat, vegetables, cheese", price = 9.99, category = "Mexican", imageUrl = "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=300&h=300&fit=crop"),
        MenuItem(id = "m30", restaurantId = "r13", name = "Caramel Macchiato", description = "Espresso with caramel and steamed milk", price = 5.99, category = "Desserts", imageUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=300&h=300&fit=crop")
    )
    
    fun getRestaurantsByCategory(category: String): List<Restaurant> {
        return if (category == "All") {
            restaurants
        } else {
            restaurants.filter { it.cuisine == category }
        }
    }
    
    fun getMenuItemsByRestaurant(restaurantId: String): List<MenuItem> {
        return menuItems.filter { it.restaurantId == restaurantId }
    }
}

