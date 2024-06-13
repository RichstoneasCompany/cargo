package com.example.richstonecargo.domain.use_case.profile_details

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.UserDetails
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProfileDetailsUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(): Flow<Resource<UserDetails>> = flow {
        try {
            Log.d("ProfileDetailsUseCase", "started")
            emit(Resource.Loading<UserDetails>())
            var result = repository.getUserDetails()
            val truckInfo = repository.getTruckInfo()
            result.truckInfo = truckInfo
            Log.d("ProfileDetailsUseCase received response", "$result")
            emit(Resource.Success<UserDetails>(result))
        } catch (e: IOException) {
            emit(Resource.Error<UserDetails>("${e.message}"))
        }
    }
}