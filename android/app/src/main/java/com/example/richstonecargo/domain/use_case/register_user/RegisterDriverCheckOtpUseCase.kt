package com.example.richstonecargo.domain.use_case.register_user

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RegisterDriverCheckOtpUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(otp: String): Flow<Resource<Unit>> = flow {
        try {
            Log.d("RegisterDriverCheckOtpUseCase", "Sending otp $otp")
            emit(Resource.Loading<Unit>())
            val result = repository.registerDriverCheckOtp(otp)
            Log.d("RegisterDriverCheckOtpUseCase", "Received response: $result")
            emit(Resource.Success(Unit))
        } catch (e: IOException) {
            Log.e("RegisterDriverCheckOtpUseCase", "Failed to send registration otp: ${e.message}")
            emit(Resource.Error<Unit>("Could not reach the server. Please check your internet connection."))
        }
    }
}