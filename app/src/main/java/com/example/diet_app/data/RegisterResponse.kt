package com.example.diet_app.data

data class RegisterResponse(
    val email: String,
    val password: String,
    val name: String,
    val gender: Boolean,
    val calories: Int,
    val streaks: Int
)
