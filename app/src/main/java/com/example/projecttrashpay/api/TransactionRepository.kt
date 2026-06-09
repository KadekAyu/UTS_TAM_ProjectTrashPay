package com.example.projecttrashpay.api

import com.example.projecttrashpay.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TransaksiFirestoreItem(
    val id: String = "",
    val jenisSampah: String = "",
    val berat: String = "",
    val poinDidapat: String = "",
    val metode: String = "",
    val lokasi: String = "",
    val tanggal: String = "",
    val image: Int = R.drawable.ic_trash,
    val createdAt: Long = 0L
)

class TransactionRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun tanggalSekarang(): String {
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(Date())
    }

    private fun iconByJenis(jenis: String): Int {
        return when (jenis.lowercase()) {
            "plastik" -> R.drawable.ic_plastic
            "botol" -> R.drawable.ic_bottle
            "kertas" -> R.drawable.ic_paper
            else -> R.drawable.ic_trash
        }
    }

    fun hitungPoin(jenis: String, berat: Double): Int {
        val poinPerKg = when (jenis.lowercase()) {
            "plastik" -> 100
            "botol" -> 100
            "kertas" -> 80
            else -> 50
        }

        return (berat * poinPerKg).toInt()
    }

    suspend fun simpanSetorSampah(
        jenisSampah: String,
        berat: String
    ): Result<String> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User belum login"))

            val beratDouble = berat.toDoubleOrNull() ?: 0.0
            val poin = hitungPoin(jenisSampah, beratDouble)

            val data = hashMapOf(
                "userId" to uid,
                "jenisSampah" to jenisSampah,
                "berat" to "$berat kg",
                "poinDidapat" to "$poin Poin",
                "poinAngka" to poin,
                "metode" to "Setor Langsung",
                "lokasi" to "Bank Sampah Terdekat",
                "tanggal" to tanggalSekarang(),
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("transactions")
                .add(data)
                .await()

            val userRef = db.collection("users").document(uid)
            val userDoc = userRef.get().await()
            val poinLama = userDoc.getLong("poin")?.toInt() ?: 0

            userRef.update("poin", poinLama + poin).await()
            runCatching {
                NotificationRepository().simpanNotifikasi(
                    title = "Setor sampah berhasil",
                    desc = "$jenisSampah sebanyak $berat kg berhasil disimpan. Kamu mendapat $poin poin."
                )
            }

            Result.success("Setor sampah berhasil")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun simpanPenjemputan(
        jenisSampah: String,
        berat: String,
        nama: String,
        telepon: String,
        alamat: String,
        catatan: String
    ): Result<String> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User belum login"))

            val beratDouble = berat.toDoubleOrNull() ?: 0.0
            val poin = hitungPoin(jenisSampah, beratDouble)

            val data = hashMapOf(
                "userId" to uid,
                "nama" to nama,
                "telepon" to telepon,
                "alamat" to alamat,
                "catatan" to catatan,
                "jenisSampah" to jenisSampah,
                "berat" to "$berat kg",
                "poinDidapat" to "$poin Poin",
                "poinAngka" to poin,
                "metode" to "Penjemputan",
                "lokasi" to alamat,
                "tanggal" to tanggalSekarang(),
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("transactions")
                .add(data)
                .await()

            val userRef = db.collection("users").document(uid)
            val userDoc = userRef.get().await()
            val poinLama = userDoc.getLong("poin")?.toInt() ?: 0

            userRef.update("poin", poinLama + poin).await()
            runCatching {
                NotificationRepository().simpanNotifikasi(
                    title = "Penjemputan berhasil diajukan",
                    desc = "Penjemputan $jenisSampah sebanyak $berat kg berhasil dibuat untuk alamat $alamat."
                )
            }

            Result.success("Data penjemputan berhasil disimpan")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRiwayatUser(): Result<List<TransaksiFirestoreItem>> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("User belum login"))

            val snapshot = db.collection("transactions")
                .whereEqualTo("userId", uid)
                .get()
                .await()

            val list = snapshot.documents.map { document ->
                val jenis = document.getString("jenisSampah") ?: "-"
                val berat = document.getString("berat") ?: "-"
                val poin = document.getString("poinDidapat") ?: "-"
                val metode = document.getString("metode") ?: "-"
                val lokasi = document.getString("lokasi") ?: "-"
                val tanggal = document.getString("tanggal") ?: "-"
                val createdAt = document.getLong("createdAt") ?: 0L

                TransaksiFirestoreItem(
                    id = document.id,
                    jenisSampah = jenis,
                    berat = berat,
                    poinDidapat = poin,
                    metode = metode,
                    lokasi = lokasi,
                    tanggal = tanggal,
                    image = iconByJenis(jenis),
                    createdAt = createdAt
                )
            }.sortedByDescending { it.createdAt }

            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}