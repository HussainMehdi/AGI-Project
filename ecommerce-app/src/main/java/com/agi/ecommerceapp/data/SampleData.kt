package com.agi.ecommerceapp.data

import com.agi.ecommerceapp.models.Product

object SampleData {
    val categories = listOf("All", "Electronics", "Fashion", "Home", "Sports", "Books", "Toys")
    
    val products = listOf(
        // Electronics
        Product(id = "p1", name = "iPhone 15 Pro 256GB", price = 999.99, description = "Latest iPhone with A17 Pro chip, 256GB storage, ProRAW, Action Button", category = "Electronics", rating = 4.8f, reviews = 1245, imageUrl = "https://images.unsplash.com/photo-1512054502232-120bbc5a0d32?w=400&h=400&fit=crop"),
        Product(id = "p2", name = "Samsung Galaxy S24 Ultra", price = 1199.99, description = "Flagship Android phone with S Pen, 200MP camera, AI features", category = "Electronics", rating = 4.7f, reviews = 892, imageUrl = "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400&h=400&fit=crop"),
        Product(id = "p3", name = "iPad Pro 12.9\" M2", price = 1099.99, description = "12.9-inch display, M2 chip, 256GB, Liquid Retina XDR", category = "Electronics", rating = 4.9f, reviews = 756, imageUrl = "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400&h=400&fit=crop"),
        Product(id = "p4", name = "Sony WH-1000XM5 Headphones", price = 399.99, description = "Premium noise-canceling wireless headphones, 30hr battery", category = "Electronics", rating = 4.6f, reviews = 2103, imageUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=400&fit=crop"),
        Product(id = "p5", name = "Samsung 65\" QLED 4K TV", price = 1299.99, description = "Smart TV with HDR10+, Quantum Dot, Gaming Hub", category = "Electronics", rating = 4.7f, reviews = 1234, imageUrl = "https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=400&h=400&fit=crop"),
        Product(id = "p6", name = "Canon EOS R5 Camera", price = 3899.99, description = "Professional mirrorless camera, 45MP, 8K video recording", category = "Electronics", rating = 4.8f, reviews = 456, imageUrl = "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400&h=400&fit=crop"),
        Product(id = "p7", name = "Apple Watch Series 9", price = 399.99, description = "Smartwatch with health tracking, Always-On display, GPS", category = "Electronics", rating = 4.7f, reviews = 2789, imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&h=400&fit=crop"),
        Product(id = "p8", name = "AirPods Pro 2", price = 249.99, description = "Active Noise Cancellation, Spatial Audio, MagSafe charging", category = "Electronics", rating = 4.8f, reviews = 3456, imageUrl = "https://images.unsplash.com/photo-1588423771073-b8903fbb85b5?w=400&h=400&fit=crop"),
        
        // Fashion
        Product(id = "p9", name = "Nike Air Max 90", price = 129.99, description = "Classic running shoes, comfortable fit, iconic design", category = "Fashion", rating = 4.5f, reviews = 3456, imageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&h=400&fit=crop"),
        Product(id = "p10", name = "Levi's 501 Original Jeans", price = 89.99, description = "Classic straight leg fit, 100% cotton denim", category = "Fashion", rating = 4.6f, reviews = 2345, imageUrl = "https://images.unsplash.com/photo-1542272604-787c3835535d?w=400&h=400&fit=crop"),
        Product(id = "p11", name = "Adidas Ultraboost 22", price = 179.99, description = "Running shoes with Boost cushioning, Primeknit upper", category = "Fashion", rating = 4.7f, reviews = 1890, imageUrl = "https://images.unsplash.com/photo-1608231387042-66d1773070a5?w=400&h=400&fit=crop"),
        Product(id = "p12", name = "Ray-Ban Aviator Sunglasses", price = 154.99, description = "Classic aviator style, UV protection, polarized lenses", category = "Fashion", rating = 4.8f, reviews = 1456, imageUrl = "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=400&h=400&fit=crop"),
        Product(id = "p13", name = "Michael Kors Handbag", price = 299.99, description = "Leather crossbody bag, adjustable strap, multiple compartments", category = "Fashion", rating = 4.5f, reviews = 987, imageUrl = "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=400&h=400&fit=crop"),
        Product(id = "p14", name = "Casio G-Shock Watch", price = 99.99, description = "Shock-resistant digital watch, water-resistant, multiple alarms", category = "Fashion", rating = 4.6f, reviews = 2100, imageUrl = "https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=400&h=400&fit=crop"),
        
        // Home
        Product(id = "p15", name = "Dyson V15 Detect Vacuum", price = 699.99, description = "Cordless vacuum with laser detection, 60min runtime", category = "Home", rating = 4.6f, reviews = 1890, imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=400&fit=crop"),
        Product(id = "p16", name = "Instant Pot Duo 7-in-1", price = 99.99, description = "Pressure cooker, slow cooker, rice cooker, yogurt maker", category = "Home", rating = 4.7f, reviews = 5678, imageUrl = "https://images.unsplash.com/photo-1556910096-6f5e72db6803?w=400&h=400&fit=crop"),
        Product(id = "p17", name = "Roomba j7+ Robot Vacuum", price = 599.99, description = "Self-emptying robot vacuum, obstacle avoidance, app control", category = "Home", rating = 4.5f, reviews = 2345, imageUrl = "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=400&h=400&fit=crop"),
        Product(id = "p18", name = "Nespresso Vertuo Coffee Maker", price = 179.99, description = "Single-serve coffee maker, 5 cup sizes, milk frother included", category = "Home", rating = 4.8f, reviews = 3456, imageUrl = "https://images.unsplash.com/photo-1517487881594-2787fef5ebf7?w=400&h=400&fit=crop"),
        Product(id = "p19", name = "Philips Hue Smart Bulbs Set", price = 149.99, description = "3-pack color bulbs, voice control, app control, dimmable", category = "Home", rating = 4.7f, reviews = 1234, imageUrl = "https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?w=400&h=400&fit=crop"),
        Product(id = "p20", name = "KitchenAid Stand Mixer", price = 429.99, description = "5-quart stand mixer, 10 speeds, multiple attachments", category = "Home", rating = 4.9f, reviews = 890, imageUrl = "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400&h=400&fit=crop"),
        
        // Sports
        Product(id = "p21", name = "Yoga Mat Premium", price = 39.99, description = "Extra thick non-slip yoga mat, carrying strap included", category = "Sports", rating = 4.6f, reviews = 2100, imageUrl = "https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?w=400&h=400&fit=crop"),
        Product(id = "p22", name = "Adjustable Dumbbells Set", price = 299.99, description = "5-50 lbs adjustable weights, compact design, quick change", category = "Sports", rating = 4.7f, reviews = 1456, imageUrl = "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400&h=400&fit=crop"),
        Product(id = "p23", name = "Bose SoundSport Headphones", price = 129.99, description = "Sweat-resistant wireless earbuds, 6hr battery, secure fit", category = "Sports", rating = 4.5f, reviews = 2345, imageUrl = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=400&h=400&fit=crop"),
        Product(id = "p24", name = "Fitbit Charge 5", price = 179.99, description = "Advanced fitness tracker, GPS, heart rate, sleep tracking", category = "Sports", rating = 4.6f, reviews = 3456, imageUrl = "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=400&h=400&fit=crop"),
        
        // Books
        Product(id = "p25", name = "The Seven Husbands of Evelyn Hugo", price = 16.99, description = "Bestselling novel by Taylor Jenkins Reid", category = "Books", rating = 4.8f, reviews = 5678, imageUrl = "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=400&fit=crop"),
        Product(id = "p26", name = "Atomic Habits by James Clear", price = 18.99, description = "An easy & proven way to build good habits", category = "Books", rating = 4.9f, reviews = 8901, imageUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400&h=400&fit=crop"),
        Product(id = "p27", name = "The Midnight Library", price = 17.99, description = "A novel by Matt Haig about second chances", category = "Books", rating = 4.7f, reviews = 4567, imageUrl = "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&h=400&fit=crop"),
        
        // Toys
        Product(id = "p28", name = "LEGO Star Wars Millennium Falcon", price = 169.99, description = "1,329 pieces, detailed model, minifigures included", category = "Toys", rating = 4.9f, reviews = 1234, imageUrl = "https://images.unsplash.com/photo-1587654780291-39c9404d746b?w=400&h=400&fit=crop"),
        Product(id = "p29", name = "Nintendo Switch OLED", price = 349.99, description = "Gaming console, 7-inch OLED screen, detachable controllers", category = "Toys", rating = 4.8f, reviews = 5678, imageUrl = "https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=400&h=400&fit=crop"),
        Product(id = "p30", name = "Rubik's Cube 3x3", price = 9.99, description = "Classic puzzle cube, smooth turning mechanism", category = "Toys", rating = 4.5f, reviews = 3456, imageUrl = "https://images.unsplash.com/photo-1606312619070-d48b4bc98b3f?w=400&h=400&fit=crop")
    )
    
    fun getProductsByCategory(category: String): List<Product> {
        return if (category == "All") {
            products
        } else {
            products.filter { it.category == category }
        }
    }
}
