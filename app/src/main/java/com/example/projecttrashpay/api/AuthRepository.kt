package com.example.projecttrashpay.api

class AuthRepository {

    suspend fun login(email: String, password: String) =
        RetrofitClient.apiService.login(
            AuthRequest(
                email = email,
                password = password
            )
        )

    suspend fun register(nama: String, email: String, password: String) =
        RetrofitClient.apiService.register(
            AuthRequest(
                nama = nama,
                email = email,
                password = password
            )
        )
}