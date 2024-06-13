package com.example.richstonecargo.domain.use_case.finish_trip

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class FinishActiveTripUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(tripId: Long): Flow<Resource<Unit>> = flow {
        try {
            Log.d("FinishActiveTripUseCase", "started")
            emit(Resource.Loading<Unit>())
            val response = repository.finishActiveTrip(tripId)
            emit(Resource.Success<Unit>(response))
        } catch (e: IOException) {
            emit(Resource.Error<Unit>("${e.message}"))
        }
    }
}