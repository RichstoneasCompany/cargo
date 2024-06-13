package com.example.richstonecargo.domain.model

import com.example.richstonecargo.data.remote.dto.CargoDto
import com.example.richstonecargo.data.remote.dto.TripDto
import com.google.gson.annotations.SerializedName

data class ActiveTripResult(
    @SerializedName("tripInformation")
    val tripInformation: TripDto?,

    @SerializedName("cargo")
    val cargo: CargoDto?
)