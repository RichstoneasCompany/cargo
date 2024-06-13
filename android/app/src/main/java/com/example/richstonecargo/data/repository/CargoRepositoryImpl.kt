package com.example.richstonecargo.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.richstonecargo.data.remote.CargoBackendApi
import com.example.richstonecargo.data.remote.dto.CargoInfo
import com.example.richstonecargo.data.remote.dto.GetUserProfilePictureResult
import com.example.richstonecargo.data.remote.dto.RegisterDriverRequest
import com.example.richstonecargo.data.remote.dto.UserInfo
import com.example.richstonecargo.data.remote.dto.toPayslipDetails
import com.example.richstonecargo.data.remote.dto.toQuestion
import com.example.richstonecargo.data.remote.dto.toSalaryInfo
import com.example.richstonecargo.data.remote.dto.toTopic
import com.example.richstonecargo.data.remote.dto.toTrip
import com.example.richstonecargo.domain.model.ForgotPasswordVerifyOtpResult
import com.example.richstonecargo.domain.model.Gender
import com.example.richstonecargo.domain.model.LoginRequest
import com.example.richstonecargo.domain.model.PayslipDetails
import com.example.richstonecargo.domain.model.Question
import com.example.richstonecargo.domain.model.SalaryInfo
import com.example.richstonecargo.domain.model.SendOtpRequest
import com.example.richstonecargo.domain.model.SendOtpResult
import com.example.richstonecargo.domain.model.Topic
import com.example.richstonecargo.domain.model.Trip
import com.example.richstonecargo.domain.model.TripChange
import com.example.richstonecargo.domain.model.TruckInfo
import com.example.richstonecargo.domain.model.UserDetails
import com.example.richstonecargo.domain.model.VerifyOtpRequest
import com.example.richstonecargo.domain.repository.CargoRepository
import com.example.richstonecargo.global.UserInfoManager
import com.example.richstonecargo.network.DynamicInterceptor
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class CargoRepositoryImpl @Inject constructor(
    private val api: CargoBackendApi
) : CargoRepository {
    private val delay = 500L
    private val isSmsMocked = false
    override suspend fun loginUser(phoneNumber: String, password: String): UserInfo {
        val request = LoginRequest(phoneNumber, password)
        Log.d("loginUser data", "$request")
        val result = api.loginUser(request)
        Log.d("loginUser success", "Logged in: $phoneNumber")
        DynamicInterceptor.token = result.access_token
        return UserInfo(
            name = result.user.name,
            surname = result.user.surname,
            access_token = result.access_token
        )
    }

    override suspend fun getUserProfilePicture(): GetUserProfilePictureResult {
        Log.d("getUserProfilePicture", "sending request...")
        val result = api.getProfilePicture()
        Log.d("getUserProfilePicture", "success: $result")
        return result
    }

    override suspend fun getUserDetails(): UserDetails {
        Log.d("getUserDetails", "sending request...")
        val result = api.getUserDetails()
        Log.d("getUserDetails", "received result: $result")

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthDate: Date? = try {
            result.birthDate?.let { dateFormat.parse(it) }
        } catch (e: Exception) {
            Log.e("getUserDetails", "Failed to parse birthDate: ${result.birthDate}", e)
            null
        }

        val gender = when (result.gender.uppercase(Locale.ROOT)) {
            "MALE" -> Gender.MALE
            "FEMALE" -> Gender.FEMALE
            else -> throw IllegalArgumentException("Invalid gender value")
        }

        return UserDetails(
            birthDate = birthDate,
            email = result.email,
            firstname = result.firstname,
            gender = gender,
            lastname = result.lastname,
            phone = result.phone,
            username = result.username,
            truckInfo = null
        )
    }

    override suspend fun getTruckInfo(): TruckInfo {
        Log.d("getTruckInfo", "sending request...")
        val result = api.getTruckInfo()
        Log.d("getTruckInfo", "received result: $result")
        return TruckInfo(
            maxPermMass = result.maxPermMass ?: "",
            model = result.model ?: "",
            numberPlate = result.numberPlate ?: ""
        )
    }

    override suspend fun getActiveTrip(): Trip {
        Log.d("getActiveTrip", "sending request...")
        val result = api.getActiveTrip()
        Log.d("getActiveTrip", "received result: $result")
        return Trip(
            cargoInfo = CargoInfo(
                result.cargo?.name ?: "-",
                result.cargo?.description ?: "-",
                result.cargo?.weight ?: 0.0,
                result.cargo?.numberOfPallets ?: 0,
                result.cargo?.temperature ?: 0.0
            ),
            arrivalDate = result.tripInformation?.arrivalTime ?: Date(),
            arrivalPlace = result.tripInformation?.endLocation ?: "-",
            departureDate = result.tripInformation?.departureTime ?: Date(),
            tripNumber = result.tripInformation?.tripNumber ?: "-",
            departurePlace = result.tripInformation?.startLocation ?: "-",
            distance = result.tripInformation?.distance ?: "-",
            duration = result.tripInformation?.duration ?: "-",
            id = result.tripInformation?.id ?: 0
        )
    }

    override suspend fun getTripById(tripId: Long): Trip {
        Log.d("getTripById", "sending request...")
        val result = api.getTripById(tripId)
        Log.d("getTripById", "received result: $result")
        return Trip(
            cargoInfo = CargoInfo(
                result.cargo?.name ?: "-",
                result.cargo?.description ?: "-",
                result.cargo?.weight ?: 0.0,
                result.cargo?.numberOfPallets ?: 0,
                result.cargo?.temperature ?: 0.0
            ),
            arrivalDate = result.tripInformation.arrivalTime ?: Date(),
            arrivalPlace = result.tripInformation.endLocation ?: "-",
            departureDate = result.tripInformation.departureTime ?: Date(),
            tripNumber = result.tripInformation.tripNumber ?: "-",
            departurePlace = result.tripInformation.startLocation ?: "-",
            distance = result.tripInformation.distance ?: "-",
            duration = result.tripInformation.duration ?: "-",
            id = result.tripInformation.id
        )
    }

    override suspend fun finishActiveTrip(tripId: Long) {
        Log.d("finishActiveTrip", "sending request...")
        api.finishActiveTrip(tripId)
        Log.d("finishActiveTrip", "success")
    }

    override suspend fun registerDriverCheckOtp(otp: String) {
        Log.d("registerDriverCheckOtp", "Sent otp $otp")
        val result = api.registerDriverCheckOtp(VerifyOtpRequest(otp))
        Log.d("registerDriverCheckOtp", "Received result $result")
    }

    override suspend fun registerDriver(phoneNumber: String, password: String) {
        val request =
            RegisterDriverRequest(phoneNumber, password, UserInfoManager.getDeviceToken() ?: "")
        Log.d("registerUser", "Sending request $request")
        val result = api.registerDriver(request)
        Log.d("registerUser", "Received result: $result")
    }

    override suspend fun sendOtp(phoneNumber: String): SendOtpResult {
        Log.d("forgotPasswordSendOtp", "sending request...")
        var result = SendOtpResult(
            status = "DELIVERED",
            message = "OTP sent successfully. Dear Rufina, Your OTP is XXXXXX. Use this password to sign into your account."
        )

        if (isSmsMocked) {
            delay(delay)
        } else {
            result = api.sendOtp(SendOtpRequest(phoneNumber))
        }

        Log.d("forgotPasswordSendOtp", "received result: $result")
        return result
    }

    override suspend fun forgotPasswordVerifyOtp(otp: String): ForgotPasswordVerifyOtpResult {
        Log.d("forgotPasswordVerifyOtp", "sending request...")

        var result =
            ForgotPasswordVerifyOtpResult(access_token = "some_access_token_from forgotPasswordVerifyOtp")
        if (isSmsMocked) {
            delay(delay)
        } else {
            result = api.forgotPasswordVerifyOtp(VerifyOtpRequest(otp))
        }

        Log.d("forgotPasswordVerifyOtp", "received result: $result")
        return result
    }

    override suspend fun forgotPasswordReset(newPassword: String, confirmPassword: String) {
        Log.d("forgotPasswordReset", "sending request...")

        if (isSmsMocked) {
            delay(delay)
        } else {
            api.forgotPasswordReset(newPassword = newPassword, confirmPassword = confirmPassword)
        }

        Log.d("forgotPasswordReset", "success")
    }

    override suspend fun getTripList(): List<Trip> {
        Log.d("getTripList", "sending request...")
        val result = api.getAvailableTrips().sortedBy { it.departureTime }
        Log.d("getTripList", "received result: $result")
        return result.map { dto ->
            dto.toTrip()
        }
    }

    override suspend fun getActiveTripRouteLink(): String {
        Log.d("getRouteLink", "sending request...")
        return try {
            val result = api.getActiveTripRouteLink().routeUrl
            Log.d("getRouteLink", "received result: $result")
            result
        } catch (e: IOException) {
            Log.e("getRouteLink", e.stackTraceToString())
            "https://example.com"
        }
    }

    override suspend fun getTopicById(topicId: Long): Topic {
        return api.getTopicById(topicId).toTopic()
    }

    override suspend fun getTopicNameById(topicId: Long): String {
        val topic = api.getTopicById(topicId)
        return topic.name
    }

    override suspend fun getTopicList(): List<Topic> {
        val result = api.getAvailableTopics()
        return result.map { it.toTopic() }
    }

    override suspend fun getQuestionsByTopicId(topicId: Long): List<Question> {
        return try {
            val result = api.getQuestions(topicId)
            Log.d("getQuestions", "received result: $result")
            result.map { it.toQuestion() }
        } catch (e: HttpException) {
            Log.e("getQuestions", "HTTP exception: ${e.message}")
            throw e
        } catch (e: IOException) {
            Log.e("getQuestions", "Network exception: ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.e("getQuestions", "Unknown exception: ${e.message}")
            throw e
        }
    }

    override suspend fun getSalaryInfo(yearMonth: String): SalaryInfo {
        Log.d("getSalaryInfo", "$yearMonth")
        val result = api.getSalaryInfo(yearMonth)
        Log.d("getSalaryInfo", "result: $result")
        return result.toSalaryInfo()
    }

    override suspend fun getAvailableMonths(): List<String> {
        return (1..12).map { month -> "2024-${month.toString().padStart(2, '0')}" }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getPayslipDetails(yearMonth: String): List<PayslipDetails> {
        return try {
            val dtos = api.getPayslipDetails(yearMonth)
            Log.d("getPayslipDetails", "Response: $dtos")
            dtos.map { it.toPayslipDetails() }
        } catch (e: HttpException) {
            Log.e("getPayslipDetails", "HTTP exception: ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.e("getPayslipDetails", "Exception: ${e.message}")
            throw e
        }
    }

    override suspend fun getTripChanges(): List<TripChange> {
        val result = api.getTripChanges().map { dto ->
            TripChange(
                title = dto.changeTitle,
                details = dto.changeDescription,
                time = dto.changeTime
            )
        }
        Log.d("getTripChanges", "$result")
        return result
    }
}
