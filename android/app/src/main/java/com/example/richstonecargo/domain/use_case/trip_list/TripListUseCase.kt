package com.example.richstonecargo.domain.use_case.trip_list

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.Trip
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class TripListUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(): Flow<Resource<List<Trip>>> = flow {
        try {
            Log.d("TripListUseCase", "started")
            emit(Resource.Loading<List<Trip>>())
            val tripList = repository.getTripList()

//            var activeTrip: Trip? = null
//            try {
//                activeTrip = repository.getActiveTrip()
//            } catch (e: HttpException) {
//                Log.e("TripListUseCase", "Active trip not found: ${e.stackTraceToString()}")
//            }

//            if (activeTrip != null) {
//                activeTrip.status = TripStatus.IN_PROGRESS
//                tripList.add(0, activeTrip)
//            }

            Log.d("TripListUseCase received response", "$tripList")
            emit(Resource.Success<List<Trip>>(tripList))
        } catch (e: IOException) {
            emit(Resource.Error<List<Trip>>("${e.message}"))
        }
    }
}