package com.example.projecttrashpay.api

data class AuthRequest(
    val email: String,
    val password: String,
    val nama: String? = null
)