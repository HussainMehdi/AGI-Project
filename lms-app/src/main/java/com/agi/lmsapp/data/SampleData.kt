package com.agi.lmsapp.data

import com.agi.lmsapp.models.Assignment
import com.agi.lmsapp.models.Course

object SampleData {
    val courses = listOf(
        Course(id = "c1", name = "Introduction to Android Development", instructor = "Prof. John Smith", description = "Learn Android app development from scratch", students = 245, assignments = 8),
        Course(id = "c2", name = "Data Structures and Algorithms", instructor = "Dr. Sarah Johnson", description = "Master fundamental algorithms and data structures", students = 180, assignments = 10),
        Course(id = "c3", name = "Machine Learning Fundamentals", instructor = "Prof. Michael Chen", description = "Introduction to ML concepts and applications", students = 320, assignments = 6),
        Course(id = "c4", name = "Web Development", instructor = "Dr. Emily Davis", description = "Build modern web applications", students = 275, assignments = 12),
        Course(id = "c5", name = "Database Systems", instructor = "Prof. David Wilson", description = "Design and manage databases", students = 195, assignments = 9),
        Course(id = "c6", name = "Software Engineering", instructor = "Dr. Jessica Brown", description = "Best practices in software development", students = 210, assignments = 7),
        Course(id = "c7", name = "Computer Networks", instructor = "Prof. Robert Taylor", description = "Understanding network protocols and architecture", students = 165, assignments = 8),
        Course(id = "c8", name = "Cybersecurity Basics", instructor = "Dr. Amanda Martinez", description = "Introduction to security principles", students = 280, assignments = 5),
        Course(id = "c9", name = "Mobile UI/UX Design", instructor = "Prof. James Anderson", description = "Design beautiful mobile interfaces", students = 230, assignments = 6),
        Course(id = "c10", name = "Cloud Computing", instructor = "Dr. Lisa Garcia", description = "Working with cloud platforms and services", students = 250, assignments = 7)
    )
    
    val assignments = listOf(
        Assignment(id = "a1", courseId = "c1", title = "Create Hello World App", description = "Build your first Android app with a simple Hello World message", dueDate = "2025-01-15", status = "Not Started"),
        Assignment(id = "a2", courseId = "c1", title = "User Interface Design", description = "Design and implement a user interface with multiple screens", dueDate = "2025-01-22", status = "In Progress"),
        Assignment(id = "a3", courseId = "c1", title = "Data Persistence", description = "Implement local data storage using Room database", dueDate = "2025-01-29", status = "Not Started"),
        Assignment(id = "a4", courseId = "c2", title = "Binary Search Tree Implementation", description = "Implement BST with insert, delete, and search operations", dueDate = "2025-01-18", status = "Submitted"),
        Assignment(id = "a5", courseId = "c2", title = "Graph Algorithms", description = "Implement BFS and DFS traversal algorithms", dueDate = "2025-01-25", status = "Not Started"),
        Assignment(id = "a6", courseId = "c3", title = "Linear Regression Model", description = "Build and train a linear regression model from scratch", dueDate = "2025-01-20", status = "In Progress"),
        Assignment(id = "a7", courseId = "c3", title = "Neural Network Basics", description = "Create a simple neural network for classification", dueDate = "2025-01-27", status = "Not Started"),
        Assignment(id = "a8", courseId = "c4", title = "HTML/CSS Website", description = "Build a responsive website using HTML and CSS", dueDate = "2025-01-16", status = "Graded"),
        Assignment(id = "a9", courseId = "c4", title = "React Application", description = "Develop a single-page application using React", dueDate = "2025-01-23", status = "In Progress"),
        Assignment(id = "a10", courseId = "c5", title = "Database Design", description = "Design a normalized database schema for an e-commerce system", dueDate = "2025-01-19", status = "Submitted"),
        Assignment(id = "a11", courseId = "c5", title = "SQL Queries", description = "Write complex SQL queries for data analysis", dueDate = "2025-01-26", status = "Not Started"),
        Assignment(id = "a12", courseId = "c6", title = "Agile Methodology Report", description = "Write a report on Agile development practices", dueDate = "2025-01-17", status = "Graded"),
        Assignment(id = "a13", courseId = "c7", title = "Network Protocol Analysis", description = "Analyze TCP/IP protocol behavior using Wireshark", dueDate = "2025-01-21", status = "In Progress"),
        Assignment(id = "a14", courseId = "c8", title = "Security Vulnerability Assessment", description = "Identify and document security vulnerabilities in a web application", dueDate = "2025-01-24", status = "Not Started"),
        Assignment(id = "a15", courseId = "c9", title = "Mobile App Mockup", description = "Create UI mockups for a mobile application", dueDate = "2025-01-18", status = "Submitted"),
        Assignment(id = "a16", courseId = "c10", title = "AWS Deployment", description = "Deploy a web application to AWS EC2", dueDate = "2025-01-28", status = "Not Started")
    )
    
    fun getAssignmentsByCourse(courseId: String): List<Assignment> {
        return assignments.filter { it.courseId == courseId }
    }
}

