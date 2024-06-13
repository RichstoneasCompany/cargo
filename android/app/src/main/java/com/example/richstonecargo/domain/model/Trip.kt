package com.example.richstonecargo.domain.model

import com.example.richstonecargo.data.remote.dto.CargoInfo
import java.util.Date


enum class TripStatus {
    NEW,
    READY,
    IN_PROGRESS,
    FINISHED,
    CANCELLED
}


data class Trip(
    val id: Long,
    val tripNumber: String,
    val departurePlace: String,
    val arrivalPlace: String,
    val departureDate: Date,
    val arrivalDate: Date,
    val cargoInfo: CargoInfo?,
    val distance: String = "",
    val duration: String = "",
    var status: TripStatus = TripStatus.NEW,
    var routeLink: String = ""
)

data class ActiveTripState(
    val isLoading: Boolean = false,
    val activeTrip: Trip? = null,
    val routeLink: String = "",
    val error: String = ""
)

data class TripListState(
    val isLoading: Boolean = false,
    val trips: List<Trip>? = emptyList(),
    val error: String = ""
)