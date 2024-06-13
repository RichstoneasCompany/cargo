package com.example.richstonecargo.data.remote

import com.example.richstonecargo.data.model.TripChangeDto
import com.example.richstonecargo.data.remote.dto.GetActiveTripRouteLinkResult
import com.example.richstonecargo.data.remote.dto.GetUserProfilePictureResult
import com.example.richstonecargo.data.remote.dto.PayslipDetailsDto
import com.example.richstonecargo.data.remote.dto.QuestionDto
import com.example.richstonecargo.data.remote.dto.RegisterDriverRequest
import com.example.richstonecargo.data.remote.dto.SalaryInfoDto
import com.example.richstonecargo.data.remote.dto.TopicDto
import com.example.richstonecargo.data.remote.dto.TripDetailsResult
import com.example.richstonecargo.data.remote.dto.TripDto
import com.example.richstonecargo.data.remote.dto.TruckInfoDto
import com.example.richstonecargo.data.remote.dto.UserDetailsDto
import com.example.richstonecargo.domain.model.ActiveTripResult
import com.example.richstonecargo.domain.model.ForgotPasswordVerifyOtpResult
import com.example.richstonecargo.domain.model.LoginRequest
import com.example.richstonecargo.domain.model.LoginResult
import com.example.richstonecargo.domain.model.SendOtpRequest
import com.example.richstonecargo.domain.model.SendOtpResult
import com.example.richstonecargo.domain.model.VerifyOtpRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CargoBackendApi {
    @GET("trips")
    suspend fun getAvailableTrips(): List<TripDto>

    @GET("topics")
    suspend fun getAvailableTopics(): List<TopicDto>

    @GET("topics/{topicId}/questions")
    suspend fun getQuestions(@Path("topicId") topicId: Long): List<QuestionDto>

    @POST("driver/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResult

    @GET("users")
    suspend fun getUserDetails(): UserDetailsDto

    @GET("images/profile")
    suspend fun getProfilePicture(): GetUserProfilePictureResult

    @POST("trips/finish")
    suspend fun finishActiveTrip(@Query("tripId") tripId: Long)

    @GET("trips/active")
    suspend fun getActiveTrip(): ActiveTripResult

    @GET("topics/{topicId}")
    suspend fun getTopicById(@Path("topicId") topicId: Long): TopicDto

    @GET("truck")
    suspend fun getTruckInfo(): TruckInfoDto

    @GET("trips/{tripId}")
    suspend fun getTripById(@Path("tripId") tripId: Long): TripDetailsResult

    @GET("routes")
    suspend fun getActiveTripRouteLink(): GetActiveTripRouteLinkResult

    @POST("auth/sendOTP")
    suspend fun sendOtp(@Body sendOtpRequest: SendOtpRequest): SendOtpResult

    @POST("auth/validateOTP")
    suspend fun forgotPasswordVerifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): ForgotPasswordVerifyOtpResult

    @POST("auth/checkOTP")
    suspend fun registerDriverCheckOtp(@Body verifyOtpRequest: VerifyOtpRequest)

    @POST("driver")
    suspend fun registerDriver(@Body registerDriver: RegisterDriverRequest)

    @FormUrlEncoded
    @PUT("users/password")
    suspend fun forgotPasswordReset(
        @Field("newPassword") newPassword: String, @Field("confirmPassword") confirmPassword: String
    )

    @GET("payslip")
    suspend fun getSalaryInfo(@Query("yearMonth") yearMonth: String): SalaryInfoDto

    @GET("payslip/details")
    suspend fun getPayslipDetails(@Query("yearMonth") yearMonth: String): List<PayslipDetailsDto>

    @GET("trips/changeHistory")
    suspend fun getTripChanges(): List<TripChangeDto>
}