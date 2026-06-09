package com.example.projecttrashpay.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class NotificationItem(
    val id: String = "",
    val title: String = "",
    val desc: String = "",
    val createdAt: Long = 0L
)

class NotificationRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun simpanNotifikasi(
        title: String,
        desc: String
    ): Result<String> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User belum login"))

            val data = hashMapOf(
                "userId" to uid,
                "title" to title,
                "desc" to desc,
                "createdAt" to System.currentTimeMillis(),
                "read" to false
            )

            db.collection("notifications")
                .add(data)
                .await()

            Result.success("Notifikasi berhasil disimpan")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNotifikasiUser(): Result<List<NotificationItem>> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User belum login"))

            val snapshot = db.collection("notifications")
                .whereEqualTo("userId", uid)
                .get()
                .await()

            val list = snapshot.documents.map { document ->
                NotificationItem(
                    id = document.id,
                    title = document.getString("title") ?: "-",
                    desc = document.getString("desc") ?: "-",
                    createdAt = document.getLong("createdAt") ?: 0L
                )
            }.sortedByDescending { it.createdAt }

            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}