package com.example.richstonecargo.data.remote.dto

data class TripDetailsResult(
    val tripInformation: TripDto,
    val cargo: CargoDto?
)
