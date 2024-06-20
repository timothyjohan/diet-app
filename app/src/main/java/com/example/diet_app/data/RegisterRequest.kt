package com.example.diet_app.data

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val gender: Boolean
) {

}