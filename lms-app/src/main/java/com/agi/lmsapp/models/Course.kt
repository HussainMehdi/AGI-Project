package com.agi.lmsapp.models

data class Course(
    val id: String,
    val name: String,
    val instructor: String,
    val description: String,
    val students: Int,
    val assignments: Int
)

