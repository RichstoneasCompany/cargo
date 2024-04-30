package com.example.richstonecargo.domain.use_case.register_user

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ReceiveRegistrationOtpUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(phoneNumber: String): Flow<Resource<Unit>> = flow {
        try {
            Log.d("ReceiveRegistrationOtpUseCase", "Sending password to: $phoneNumber")
            emit(Resource.Loading<Unit>())
            val result = repository.receiveRegistrationOtp(phoneNumber)
            Log.d("ReceiveRegistrationOtpUseCase", "Received response: $result")
            emit(Resource.Success(Unit))
        } catch (e: IOException) {
            Log.e("ReceiveRegistrationOtpUseCase", "Failed to receive OTP: ${e.message}")
            emit(Resource.Error<Unit>("Could not reach the server. Please check your internet connection."))
        }
    }
}