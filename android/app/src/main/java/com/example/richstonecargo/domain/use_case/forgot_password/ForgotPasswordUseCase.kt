package com.example.richstonecargo.domain.use_case.forgot_password

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.SendOtpResult
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(phoneNumber: String): Flow<Resource<SendOtpResult>> = flow {
        try {
            Log.d("ForgotPasswordUseCase", "$phoneNumber")
            emit(Resource.Loading<SendOtpResult>())
            val user = repository.sendOtp(phoneNumber)
            Log.d("ForgotPasswordUseCase received response", "$user")
            emit(Resource.Success<SendOtpResult>(user))
        } catch (e: IOException) {
            emit(Resource.Error<SendOtpResult>("${e.message}"))
        }
    }
}