package com.example.richstonecargo.data.remote.dto

data class CargoInfo(
    val name: String,
    val description: String,
    val weight: Double,
    val numberOfPallets: Int,
    val temperature: Double
)
