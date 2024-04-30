package com.example.richstonecargo.domain.repository

import com.example.richstonecargo.data.remote.dto.Trip
import com.example.richstonecargo.data.remote.dto.UserInfo

interface CargoRepository {
    suspend fun loginUser(phoneNumber: String, password: String): UserInfo
    suspend fun getTripList(): List<Trip>
    suspend fun receiveRegistrationOtp(phoneNumber: String)

    suspend fun sendRegistrationOtp(phoneNumber: String, otp: String)

    suspend fun registerUser(phoneNumber: String, password: String)
}
