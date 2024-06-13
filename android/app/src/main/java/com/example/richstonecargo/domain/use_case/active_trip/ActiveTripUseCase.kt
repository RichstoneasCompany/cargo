package com.example.richstonecargo.domain.use_case.active_trip

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.Trip
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ActiveTripUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(): Flow<Resource<Trip>> = flow {
        try {
            Log.d("ActiveTripUseCase", "started")
            emit(Resource.Loading<Trip>())
            val activeTripResponse = repository.getActiveTrip()
            try {
                val routeUrl = repository.getActiveTripRouteLink()
                activeTripResponse.routeLink = routeUrl
            } catch (e: Error) {
                Log.e("routeLinkResponse", "${e.message}")
            }
            Log.d("ActiveTripUseCase received response", "$activeTripResponse")
            emit(Resource.Success<Trip>(activeTripResponse))
        } catch (e: IOException) {
            emit(Resource.Error<Trip>("${e.message}"))
        }
    }
}