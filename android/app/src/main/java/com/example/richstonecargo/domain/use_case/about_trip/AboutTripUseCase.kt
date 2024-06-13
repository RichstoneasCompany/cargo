package com.example.richstonecargo.domain.use_case.about_trip

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.Trip
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AboutTripUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(tripId: Long): Flow<Resource<Trip>> = flow {
        try {
            Log.d("AboutTripUseCase", "started")
            emit(Resource.Loading<Trip>())
            val response = repository.getTripById(tripId)
            Log.d("AboutTripUseCase received response", "$response")
            emit(Resource.Success<Trip>(response))
        } catch (e: IOException) {
            emit(Resource.Error<Trip>("${e.message}"))
        }
    }
}