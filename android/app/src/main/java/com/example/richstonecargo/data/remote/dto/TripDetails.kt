package com.example.richstonecargo.data.remote.dto

data class TripDetails(
    val tripNumber: String,
    val loadPoint: String,
    val unloadPoint: String,
    val loadDate: String,
    val loadTime: String,
    val distance: String,
    val tripTime: String
)