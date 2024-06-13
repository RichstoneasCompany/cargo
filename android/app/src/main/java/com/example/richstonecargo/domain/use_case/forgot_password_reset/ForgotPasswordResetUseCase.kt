package com.example.richstonecargo.domain.use_case.forgot_password_reset

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ForgotPasswordResetUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(newPassword: String, confirmPassword: String): Flow<Resource<Unit>> = flow {
        try {
            Log.d(
                "ForgotPasswordResetUseCase",
                "newPassword=$newPassword, confirmPassword=$confirmPassword"
            )
            emit(Resource.Loading<Unit>())
            repository.forgotPasswordReset(
                newPassword = newPassword,
                confirmPassword = confirmPassword
            )
            Log.d("ForgotPasswordResetUseCase received response", "Success!!!")
            emit(Resource.Success<Unit>(Unit))
        } catch (e: IOException) {
            emit(Resource.Error<Unit>("${e.message}"))
        }
    }
}