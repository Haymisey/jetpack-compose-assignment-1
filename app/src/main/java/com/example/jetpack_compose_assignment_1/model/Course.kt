package com.example.jetpack_compose_assignment_1.model

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: List<String>
)