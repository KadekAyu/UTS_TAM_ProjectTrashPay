package com.example.projecttrashpay.model

import com.example.projecttrashpay.R

data class Riwayat(
    val id: Int,
    val jenisSampah: String,
    val berat: String,
    val poinDidapat: String,
    val image: Int,
    val metode: String,
    val lokasi: String,
    val tanggal: String
)

val riwayatList = listOf(
    Riwayat(1, "Botol", "2.5 kg", "250 Poin", R.drawable.botol, "Setor Langsung", "B.S. Pahoman", "10 Januari 2026"),
    Riwayat(2, "Kertas", "1.5 kg", "120 Poin", R.drawable.kertas, "Penjemputan", "Rumah - Jl. Mawar No. 5", "12 Januari 2026"),
    Riwayat(3, "Plastik", "5.5 kg", "550 Poin", R.drawable.plastic, "Setor Langsung", "B.S. Kedaton", "15 Januari 2026")
)