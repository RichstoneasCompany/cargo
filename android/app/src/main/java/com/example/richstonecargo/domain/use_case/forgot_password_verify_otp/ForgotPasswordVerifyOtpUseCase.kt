package com.example.richstonecargo.domain.use_case.forgot_password_verify_otp

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.ForgotPasswordVerifyOtpResult
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ForgotPasswordVerifyOtpUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(otp: String): Flow<Resource<ForgotPasswordVerifyOtpResult>> = flow {
        try {
            Log.d("ForgotPasswordVerifyOtpUseCase", otp)
            emit(Resource.Loading<ForgotPasswordVerifyOtpResult>())
            val result = repository.forgotPasswordVerifyOtp(otp)
            Log.d("ForgotPasswordVerifyOtpUseCase received response", "$result")
            emit(Resource.Success<ForgotPasswordVerifyOtpResult>(result))
        } catch (e: IOException) {
            emit(Resource.Error<ForgotPasswordVerifyOtpResult>("${e.message}"))
        }
    }
}