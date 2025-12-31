package com.agi.marketplaceapp.data

import com.agi.marketplaceapp.models.App

object SampleData {
    val categories = listOf("All", "Games", "Productivity", "Social", "Entertainment", "Education", "Business")
    
    val apps = listOf(
        App(id = "a1", name = "WhatsApp", developer = "WhatsApp Inc.", category = "Social", rating = 4.5f, downloads = "5B+", description = "Simple. Reliable. Secure messaging and calling for free", price = 0.0, size = "100 MB", iconUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=200&h=200&fit=crop"),
        App(id = "a2", name = "Instagram", developer = "Meta", category = "Social", rating = 4.6f, downloads = "1B+", description = "Share photos and videos with friends", price = 0.0, size = "150 MB", iconUrl = "https://images.unsplash.com/photo-1611262588024-d12430b98920?w=200&h=200&fit=crop"),
        App(id = "a3", name = "TikTok", developer = "ByteDance", category = "Entertainment", rating = 4.4f, downloads = "1B+", description = "Create and discover short videos", price = 0.0, size = "200 MB", iconUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=200&h=200&fit=crop"),
        App(id = "a4", name = "Candy Crush Saga", developer = "King", category = "Games", rating = 4.6f, downloads = "1B+", description = "Sweet match 3 puzzle game", price = 0.0, size = "150 MB", iconUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200&h=200&fit=crop"),
        App(id = "a5", name = "Spotify", developer = "Spotify AB", category = "Entertainment", rating = 4.7f, downloads = "500M+", description = "Music and podcasts streaming", price = 0.0, size = "80 MB", iconUrl = "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=200&h=200&fit=crop"),
        App(id = "a6", name = "Microsoft Word", developer = "Microsoft Corporation", category = "Productivity", rating = 4.5f, downloads = "1B+", description = "Create and edit documents", price = 0.0, size = "300 MB", iconUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=200&h=200&fit=crop"),
        App(id = "a7", name = "Zoom", developer = "Zoom Video Communications", category = "Business", rating = 4.3f, downloads = "500M+", description = "Video conferencing and meetings", price = 0.0, size = "120 MB", iconUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=200&h=200&fit=crop"),
        App(id = "a8", name = "Duolingo", developer = "Duolingo", category = "Education", rating = 4.7f, downloads = "500M+", description = "Learn languages for free", price = 0.0, size = "100 MB", iconUrl = "https://images.unsplash.com/photo-1523240795612-9a054b0db644?w=200&h=200&fit=crop"),
        App(id = "a9", name = "PUBG Mobile", developer = "PUBG Corporation", category = "Games", rating = 4.3f, downloads = "1B+", description = "Battle royale game", price = 0.0, size = "2 GB", iconUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200&h=200&fit=crop"),
        App(id = "a10", name = "Netflix", developer = "Netflix, Inc.", category = "Entertainment", rating = 4.4f, downloads = "500M+", description = "Watch TV shows and movies", price = 0.0, size = "50 MB", iconUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=200&h=200&fit=crop"),
        App(id = "a11", name = "Gmail", developer = "Google LLC", category = "Productivity", rating = 4.6f, downloads = "5B+", description = "Email service by Google", price = 0.0, size = "60 MB", iconUrl = "https://images.unsplash.com/photo-1611262588024-d12430b98920?w=200&h=200&fit=crop"),
        App(id = "a12", name = "Telegram", developer = "Telegram FZ-LLC", category = "Social", rating = 4.5f, downloads = "500M+", description = "Fast and secure messaging", price = 0.0, size = "80 MB", iconUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=200&h=200&fit=crop"),
        App(id = "a13", name = "YouTube", developer = "Google LLC", category = "Entertainment", rating = 4.4f, downloads = "5B+", description = "Watch and share videos", price = 0.0, size = "70 MB", iconUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=200&h=200&fit=crop"),
        App(id = "a14", name = "Clash of Clans", developer = "Supercell", category = "Games", rating = 4.6f, downloads = "500M+", description = "Build your village and battle", price = 0.0, size = "400 MB", iconUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200&h=200&fit=crop"),
        App(id = "a15", name = "Notion", developer = "Notion Labs Inc.", category = "Productivity", rating = 4.7f, downloads = "50M+", description = "All-in-one workspace", price = 0.0, size = "100 MB", iconUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=200&h=200&fit=crop"),
        App(id = "a16", name = "LinkedIn", developer = "LinkedIn Corporation", category = "Business", rating = 4.4f, downloads = "500M+", description = "Professional networking", price = 0.0, size = "120 MB", iconUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=200&h=200&fit=crop"),
        App(id = "a17", name = "Khan Academy", developer = "Khan Academy", category = "Education", rating = 4.6f, downloads = "50M+", description = "Free educational content", price = 0.0, size = "60 MB", iconUrl = "https://images.unsplash.com/photo-1523240795612-9a054b0db644?w=200&h=200&fit=crop"),
        App(id = "a18", name = "Among Us", developer = "InnerSloth LLC", category = "Games", rating = 4.2f, downloads = "500M+", description = "Social deduction game", price = 0.0, size = "200 MB", iconUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200&h=200&fit=crop"),
        App(id = "a19", name = "Discord", developer = "Discord Inc.", category = "Social", rating = 4.5f, downloads = "100M+", description = "Voice and text chat", price = 0.0, size = "90 MB", iconUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=200&h=200&fit=crop"),
        App(id = "a20", name = "Slack", developer = "Slack Technologies", category = "Business", rating = 4.3f, downloads = "100M+", description = "Team communication tool", price = 0.0, size = "110 MB", iconUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=200&h=200&fit=crop"),
        App(id = "a21", name = "Facebook", developer = "Meta", category = "Social", rating = 4.1f, downloads = "5B+", description = "Connect with friends", price = 0.0, size = "200 MB", iconUrl = "https://images.unsplash.com/photo-1611262588024-d12430b98920?w=200&h=200&fit=crop"),
        App(id = "a22", name = "Adobe Acrobat Reader", developer = "Adobe", category = "Productivity", rating = 4.4f, downloads = "1B+", description = "PDF viewer and editor", price = 0.0, size = "150 MB", iconUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=200&h=200&fit=crop"),
        App(id = "a23", name = "Minecraft", developer = "Mojang", category = "Games", rating = 4.7f, downloads = "500M+", description = "Build and explore worlds", price = 6.99, size = "500 MB", iconUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200&h=200&fit=crop"),
        App(id = "a24", name = "Coursera", developer = "Coursera", category = "Education", rating = 4.6f, downloads = "50M+", description = "Online courses and degrees", price = 0.0, size = "80 MB", iconUrl = "https://images.unsplash.com/photo-1523240795612-9a054b0db644?w=200&h=200&fit=crop"),
        App(id = "a25", name = "Twitter", developer = "Twitter, Inc.", category = "Social", rating = 4.2f, downloads = "500M+", description = "What's happening in the world", price = 0.0, size = "140 MB", iconUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=200&h=200&fit=crop"),
        App(id = "a26", name = "Snapchat", developer = "Snap Inc.", category = "Social", rating = 4.1f, downloads = "1B+", description = "Share moments with friends", price = 0.0, size = "180 MB", iconUrl = "https://images.unsplash.com/photo-1611262588024-d12430b98920?w=200&h=200&fit=crop"),
        App(id = "a27", name = "Photoshop Express", developer = "Adobe", category = "Productivity", rating = 4.5f, downloads = "500M+", description = "Photo editing made easy", price = 0.0, size = "250 MB", iconUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=200&h=200&fit=crop"),
        App(id = "a28", name = "Call of Duty Mobile", developer = "Activision", category = "Games", rating = 4.4f, downloads = "500M+", description = "FPS mobile game", price = 0.0, size = "1.5 GB", iconUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=200&h=200&fit=crop"),
        App(id = "a29", name = "Trello", developer = "Atlassian", category = "Business", rating = 4.5f, downloads = "50M+", description = "Organize and collaborate", price = 0.0, size = "70 MB", iconUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=200&h=200&fit=crop"),
        App(id = "a30", name = "PowerPoint", developer = "Microsoft Corporation", category = "Productivity", rating = 4.5f, downloads = "1B+", description = "Create presentations", price = 0.0, size = "280 MB", iconUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=200&h=200&fit=crop")
    )
    
    fun getAppsByCategory(category: String): List<App> {
        return if (category == "All") {
            apps
        } else {
            apps.filter { it.category == category }
        }
    }
}

