package com.example.richstonecargo.data.remote.dto

import com.example.richstonecargo.domain.model.Trip
import com.example.richstonecargo.domain.model.TripStatus
import com.google.gson.annotations.SerializedName
import java.util.Date

data class TripDto(
    @SerializedName("id") val id: Long,
    @SerializedName("tripNumber") val tripNumber: String,
    @SerializedName("departureTime") val departureTime: Date,
    @SerializedName("arrivalTime") val arrivalTime: Date,
    @SerializedName("startLocation") val startLocation: String,
    @SerializedName("endLocation") val endLocation: String,
    @SerializedName("distance") val distance: String? = null,
    @SerializedName("duration") val duration: String? = null,
    @SerializedName("tripStatus") val tripStatus: TripStatus? = null
)

fun TripDto.toTrip(): Trip {
    return Trip(
        tripNumber = this.tripNumber,
        departurePlace = this.startLocation,
        arrivalPlace = this.endLocation,
        departureDate = this.departureTime,
        arrivalDate = this.arrivalTime,
        distance = this.distance ?: "",
        duration = this.duration ?: "",
        cargoInfo = null,
        status = this.tripStatus ?: TripStatus.NEW,
        id = this.id
    )
}
