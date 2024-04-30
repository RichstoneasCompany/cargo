package com.example.richstonecargo.domain.use_case.register_user

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(phoneNumber: String, password: String): Flow<Resource<Unit>> = flow {
        try {
            Log.d("RegisterUserUseCase", "Registering $phoneNumber $password")
            emit(Resource.Loading<Unit>())
            val result = repository.registerUser(phoneNumber, password)
            Log.d("RegisterUserUseCase", "Received response: $result")
            emit(Resource.Success(Unit))
        } catch (e: IOException) {
            Log.e("RegisterUserUseCase", "Failed to register user: ${e.message}")
            emit(Resource.Error<Unit>("Could not reach the server. Please check your internet connection."))
        }
    }
}