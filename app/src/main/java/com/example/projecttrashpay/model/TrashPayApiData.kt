package com.example.projecttrashpay.model

data class TrashPayApiData(
    val jenisSampah: List<JenisSampahApi> = emptyList(),
    val reward: List<RewardApi> = emptyList(),
    val panduan: List<String> = emptyList()
)

data class JenisSampahApi(
    val nama: String = "",
    val poinPerKg: Int = 0
)

data class RewardApi(
    val nama: String = "",
    val poin: Int = 0
)