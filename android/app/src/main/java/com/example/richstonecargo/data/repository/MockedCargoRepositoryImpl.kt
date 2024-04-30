package com.example.richstonecargo.data.repository

import android.util.Log
import com.example.richstonecargo.data.remote.CargoBackendApi
import com.example.richstonecargo.data.remote.dto.CargoInfo
import com.example.richstonecargo.data.remote.dto.Trip
import com.example.richstonecargo.data.remote.dto.UserInfo
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject


class MockedCargoRepositoryImpl @Inject constructor(
    private val api: CargoBackendApi
) : CargoRepository {
    private val delay = 500L
    override suspend fun loginUser(phoneNumber: String, password: String): UserInfo {
        delay(delay)
        Log.d("loginUser", "Logged in: $phoneNumber")
        return UserInfo(
            id = 1,
            access_token = "Mocked qweasd",
            name = "Mocked Rufinik",
            surname = "Mocked Saiputulina"
        )
    }

    override suspend fun receiveRegistrationOtp(phoneNumber: String) {
        delay(delay)
        Log.d("receiveRegistrationOtp", "Received otp to: $phoneNumber")
    }

    override suspend fun sendRegistrationOtp(phoneNumber: String, otp: String) {
        delay(delay)
        Log.d("sendRegistrationOtp", "Sent otp of: $phoneNumber $otp")
    }

    override suspend fun registerUser(phoneNumber: String, password: String) {
        delay(delay)
        Log.d("registerUser", "Registered user: $phoneNumber $password")
    }

    override suspend fun getTripList(): List<Trip> {
        return listOf(createExampleTrip())
    }

    private fun createExampleTrip(): Trip {
        // Generate some random ID for the trip, typically this would be done by your database
        val tripId = List(10) { (('A'..'Z') + ('0'..'9')).random() }.joinToString("")

        val cargo = CargoInfo(
            id = "CRG-1",
            name = "Electronics",
            description = "Consumer electronics",
            totalWeight = 1500.0, // Weight in kg
            palletCount = 10,
            temperatureMode = "Ambient" // Just an example, could be "Refrigerated" etc.
        )

        val departureDate = Date() // Current date and time
        val arrivalDate = Date(departureDate.time + 1000 * 60 * 60 * 24) // Plus one day for example

        return Trip(
            id = tripId,
            tripNumber = "KZ-ALM-KHR-001",
            departurePlace = "Khorgos",
            arrivalPlace = "Almaty",
            departureDate = departureDate,
            arrivalDate = arrivalDate,
            cargoInfo = cargo
        )
    }
}