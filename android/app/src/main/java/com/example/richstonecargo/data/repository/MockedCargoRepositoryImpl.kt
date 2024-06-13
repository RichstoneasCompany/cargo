package com.example.richstonecargo.data.repository

import android.util.Log
import com.example.richstonecargo.data.remote.CargoBackendApi
import com.example.richstonecargo.data.remote.dto.CargoInfo
import com.example.richstonecargo.data.remote.dto.GetUserProfilePictureResult
import com.example.richstonecargo.data.remote.dto.UserInfo
import com.example.richstonecargo.data.remote.dto.toTopic
import com.example.richstonecargo.domain.model.ForgotPasswordVerifyOtpResult
import com.example.richstonecargo.domain.model.PayslipDetails
import com.example.richstonecargo.domain.model.Question
import com.example.richstonecargo.domain.model.SalaryInfo
import com.example.richstonecargo.domain.model.SendOtpResult
import com.example.richstonecargo.domain.model.Topic
import com.example.richstonecargo.domain.model.Trip
import com.example.richstonecargo.domain.model.TripChange
import com.example.richstonecargo.domain.model.TruckInfo
import com.example.richstonecargo.domain.model.UserDetails
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject


class MockedCargoRepositoryImpl @Inject constructor(
    private val api: CargoBackendApi
) : CargoRepository {
    private val delay = 500L
    override suspend fun loginUser(phoneNumber: String, password: String): UserInfo {
        Log.d("loginUser", "Logged in: $phoneNumber")

        return UserInfo(
            access_token = "hehehe",
            name = "Mocked",
            surname = "User"
        )
    }

    override suspend fun getUserProfilePicture(): GetUserProfilePictureResult {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDetails(): UserDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getTruckInfo(): TruckInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveTrip(): Trip {
        return Trip(
            1,
            "T-001",
            "Almaty",
            "Horgos",
            Date(),
            Date(),
            CargoInfo("gruz", "description", 0.0, 1, 20.1),
            "323km",
            "5 hours",
        )
    }

    override suspend fun getTripById(tripId: Long): Trip {
        TODO("Not yet implemented")
    }

    override suspend fun finishActiveTrip(tripId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getTopicById(topicId: Long): Topic {
        TODO("Not yet implemented")
    }

    override suspend fun getTopicNameById(topicId: Long): String {
        TODO("Not yet implemented")
    }

    override suspend fun getTopicList(): List<Topic> {
        val result = api.getAvailableTopics()
        return result.map { dto -> dto.toTopic() }
    }

    override suspend fun getQuestionsByTopicId(id: Long): List<Question> {
        return emptyList()
    }

    override suspend fun sendOtp(phoneNumber: String): SendOtpResult {
        TODO("Not yet implemented")
    }

    override suspend fun registerDriverCheckOtp(otp: String) {
        TODO("Not yet implemented")
    }


    override suspend fun registerDriver(phoneNumber: String, password: String) {
        delay(delay)
        Log.d("registerUser", "Registered user: $phoneNumber $password")
    }


    override suspend fun forgotPasswordVerifyOtp(otp: String): ForgotPasswordVerifyOtpResult {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPasswordReset(newPassword: String, confirmPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTripList(): List<Trip> {
        return listOf()
    }

    override suspend fun getActiveTripRouteLink(): String {
        return "https://example.com"
    }

    private fun createExampleTrip(): Trip {
        // Generate some random ID for the trip, typically this would be done by your database
        val tripId = List(10) { (('A'..'Z') + ('0'..'9')).random() }.joinToString("")

        val cargo = CargoInfo(
            name = "Electronics",
            description = "Consumer electronics",
            weight = 1500.0, // Weight in kg
            numberOfPallets = 10,
            temperature = 20.0 // Just an example, could be "Refrigerated" etc.
        )

        val departureDate = Date() // Current date and time
        val arrivalDate = Date(departureDate.time + 1000 * 60 * 60 * 24) // Plus one day for example

        return Trip(
            id = 1,
            tripNumber = "KZ-ALM-KHR-001",
            departurePlace = "Khorgos",
            arrivalPlace = "Almaty",
            departureDate = departureDate,
            arrivalDate = arrivalDate,
            cargoInfo = cargo,
            duration = "1h",
            distance = "100km"
        )
    }

    override suspend fun getSalaryInfo(month: String): SalaryInfo {
        return SalaryInfo(
            baseSalary = 200000,
            bonuses = 50000,
            fine = 10000,
            incomeTax = 30000,
            totalPay = 210000
        )
    }

    override suspend fun getAvailableMonths(): List<String> {
        return listOf(
            "Декабрь 2024",
            "Январь 2024",
            "Февраль 2024",
            "Март 2024",
            "Апрель 2024",
            "Май 2024",
            "Июнь 2024",
            "Июль 2024",
            "Август 2024",
            "Сентябрь 2024",
            "Октябрь 2024",
            "Ноябрь 2024"
        )
    }

    override suspend fun getPayslipDetails(yearMonth: String): List<PayslipDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getTripChanges(): List<TripChange> {
        TODO("Not yet implemented")
    }
}