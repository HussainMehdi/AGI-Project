package com.agi.lmsapp.models

data class Assignment(
    val id: String,
    val courseId: String,
    val title: String,
    val description: String,
    val dueDate: String, // yyyy-MM-dd
    val status: String // Not Started, In Progress, Submitted, Graded
)

