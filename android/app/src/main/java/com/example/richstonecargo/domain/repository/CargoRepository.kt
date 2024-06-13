package com.example.richstonecargo.domain.repository

import com.example.richstonecargo.data.remote.dto.GetUserProfilePictureResult
import com.example.richstonecargo.data.remote.dto.UserInfo
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

interface CargoRepository {
    suspend fun loginUser(phoneNumber: String, password: String): UserInfo
    suspend fun getUserProfilePicture(): GetUserProfilePictureResult
    suspend fun getUserDetails(): UserDetails

    suspend fun getTruckInfo(): TruckInfo
    suspend fun getActiveTrip(): Trip
    suspend fun getTripById(tripId: Long): Trip
    suspend fun finishActiveTrip(tripId: Long)

    suspend fun getTripList(): List<Trip>

    suspend fun getActiveTripRouteLink(): String

    suspend fun registerDriver(phoneNumber: String, password: String)
    suspend fun getTopicById(topicId: Long): Topic
    suspend fun getTopicNameById(topicId: Long): String
    suspend fun getTopicList(): List<Topic>
    suspend fun getQuestionsByTopicId(topicId: Long): List<Question>

    suspend fun sendOtp(phoneNumber: String): SendOtpResult
    suspend fun registerDriverCheckOtp(otp: String)
    suspend fun forgotPasswordVerifyOtp(otp: String): ForgotPasswordVerifyOtpResult

    suspend fun forgotPasswordReset(newPassword: String, confirmPassword: String)
    suspend fun getSalaryInfo(month: String): SalaryInfo
    suspend fun getAvailableMonths(): List<String>
    suspend fun getPayslipDetails(yearMonth: String): List<PayslipDetails>
    suspend fun getTripChanges(): List<TripChange>

}
