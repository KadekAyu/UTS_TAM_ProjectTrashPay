package com.example.projecttrashpay.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.projecttrashpay.model.TrashPayApiData
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body request: AuthRequest
    ): Response<AuthResponse>

    @GET
    suspend fun getTrashPayData(
        @Url url: String
    ): TrashPayApiData

    @POST("register")
    suspend fun register(
        @Body request: AuthRequest
    ): Response<AuthResponse>
}