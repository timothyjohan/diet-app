package com.example.diet_app.data.source

data class RegisterResponse(
    val email: String,
    val name: String,
    val gender: Boolean,
    val calories: Int,
    val streaks: Int
)
