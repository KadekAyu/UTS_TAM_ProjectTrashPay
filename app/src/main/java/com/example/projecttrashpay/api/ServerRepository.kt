package com.example.projecttrashpay.api

import com.example.projecttrashpay.model.TrashPayApiData

class ServerRepository {

    private val api = RetrofitClient.apiService

    private val gistUrl = "https://gist.githubusercontent.com/mdinz/59643f718451090c9cb4ff59e4efc111/raw/"

    suspend fun getTrashPayData(): Result<TrashPayApiData> {
        return try {
            val data = api.getTrashPayData(gistUrl)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}