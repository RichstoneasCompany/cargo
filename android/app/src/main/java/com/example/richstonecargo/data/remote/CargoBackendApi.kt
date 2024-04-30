package com.example.richstonecargo.data.remote

import com.example.richstonecargo.data.remote.dto.Trip
import com.example.richstonecargo.data.remote.dto.UserInfo
import retrofit2.http.GET
import retrofit2.http.POST

interface CargoBackendApi {
    @GET("/trip")
    suspend fun getTripList(): List<Trip>

    @POST("/login")
    suspend fun loginUser(): UserInfo

    @POST("/registration/receiveOtp")
    suspend fun registrationReceiveSms(mobileNumber: String)

    @POST("/registration/sendOtp")
    suspend fun registrationSendSms(mobileNumber: String, otp: String)
}