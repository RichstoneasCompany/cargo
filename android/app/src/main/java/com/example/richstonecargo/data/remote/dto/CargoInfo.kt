package com.example.richstonecargo.data.remote.dto

data class CargoInfo(
    val id: String,
    val name: String,
    val description: String,
    val totalWeight: Double,
    val palletCount: Int,
    val temperatureMode: String
)
