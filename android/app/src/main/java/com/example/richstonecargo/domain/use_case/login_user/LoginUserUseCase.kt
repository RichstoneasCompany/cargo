package com.example.richstonecargo.domain.use_case.login_user

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.data.remote.dto.UserInfo
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(phoneNumber: String, password: String): Flow<Resource<UserInfo>> = flow {
        try {
            Log.d("LoginUserUseCase.loginUser", "$phoneNumber")
            emit(Resource.Loading<UserInfo>())
            val user = repository.loginUser(phoneNumber, password)
            try {
                val profilePictureResult = repository.getUserProfilePicture()
                user.profilePicture = profilePictureResult.imageBytes
            } catch (e: Error) {
                Log.e("profilePictureResult", "Error: ${e.message}")
            }

            Log.d("LoginUserUseCase.loginUser received response", "$user")
            emit(Resource.Success<UserInfo>(user))
        } catch (e: IOException) {
            emit(Resource.Error<UserInfo>("${e.message}"))
        }
    }
}