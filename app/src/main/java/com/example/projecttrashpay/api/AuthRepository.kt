package com.example.projecttrashpay.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun register(
        nama: String,
        email: String,
        telepon: String,
        password: String
    ): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: ""

            val userData = hashMapOf(
                "uid" to uid,
                "nama" to nama,
                "email" to email,
                "telepon" to telepon,
                "poin" to 0
            )

            db.collection("users")
                .document(uid)
                .set(userData)
                .await()

            Result.success("Register berhasil")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUserPhone(): String {
        val uid = auth.currentUser?.uid ?: return "-"

        return try {
            val document = db.collection("users")
                .document(uid)
                .get()
                .await()

            document.getString("telepon") ?: "-"
        } catch (e: Exception) {
            "-"
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success("Login berhasil")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Result<String> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success("Link reset password sudah dikirim ke email")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

    suspend fun getCurrentUserName(): String {
        val uid = auth.currentUser?.uid ?: return "User"

        return try {
            val document = db.collection("users")
                .document(uid)
                .get()
                .await()

            document.getString("nama") ?: "User"
        } catch (e: Exception) {
            "User"
        }
    }

    suspend fun getCurrentUserPoint(): Int {
        val uid = auth.currentUser?.uid ?: return 0

        return try {
            val document = db.collection("users")
                .document(uid)
                .get()
                .await()

            document.getLong("poin")?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    suspend fun tukarPoin(
        namaHadiah: String,
        jumlahPoin: Int
    ): Result<Int> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User belum login"))

            val userRef = db.collection("users").document(uid)
            val userDoc = userRef.get().await()

            val poinSaatIni = userDoc.getLong("poin")?.toInt() ?: 0

            if (poinSaatIni < jumlahPoin) {
                return Result.failure(Exception("Poin tidak cukup"))
            }

            val sisaPoin = poinSaatIni - jumlahPoin

            userRef.update("poin", sisaPoin).await()

            val dataPenukaran = hashMapOf(
                "userId" to uid,
                "namaHadiah" to namaHadiah,
                "jumlahPoin" to jumlahPoin,
                "sisaPoin" to sisaPoin,
                "status" to "Berhasil",
                "tanggal" to System.currentTimeMillis()
            )

            db.collection("redeems")
                .add(dataPenukaran)
                .await()

            runCatching {
                NotificationRepository().simpanNotifikasi(
                    title = "Penukaran berhasil",
                    desc = "$namaHadiah berhasil ditukar. Sisa poin kamu $sisaPoin poin."
                )
            }

            Result.success(sisaPoin)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}