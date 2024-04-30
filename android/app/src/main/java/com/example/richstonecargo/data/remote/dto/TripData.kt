package com.example.richstonecargo.data.remote.dto

data class TripData(
    val date: String,
    val year: String,
    val month: String,
    val tripNumber: String,
    val route: String,
    val loadingTime: String,
    val tripTime: String,
    val isCurrentCargo: Boolean
)
