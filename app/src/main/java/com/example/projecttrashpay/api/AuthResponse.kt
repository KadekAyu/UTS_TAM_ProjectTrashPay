package com.example.projecttrashpay.api

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val nama: String? = null,
    val email: String? = null
)