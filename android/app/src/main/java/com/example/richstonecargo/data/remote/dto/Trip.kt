package com.example.richstonecargo.data.remote.dto

import java.util.Date

data class Trip(
    val id: String,
    val tripNumber: String,
    val departurePlace: String,
    val arrivalPlace: String,
    val departureDate: Date,
    val arrivalDate: Date,
    val cargoInfo: CargoInfo
)