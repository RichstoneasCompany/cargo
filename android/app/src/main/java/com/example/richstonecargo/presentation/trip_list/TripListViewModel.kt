package com.example.richstonecargo.presentation.trip_list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.data.remote.dto.PayslipDto
import com.example.richstonecargo.data.remote.dto.TripData
import com.example.richstonecargo.domain.model.TripListState
import com.example.richstonecargo.domain.use_case.trip_list.TripListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TripListViewModel @Inject constructor(
    private val tripListUseCase: TripListUseCase
) : ViewModel() {
    private val _state = MutableLiveData<TripListState>()
    val state: LiveData<TripListState> = _state

    init {
        loadTrips()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTrips() {
        viewModelScope.launch {
            tripListUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = TripListState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value = TripListState(trips = result.data)
                        }

                        is Resource.Error -> {
                            _state.value = TripListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
                .catch { e ->
                    Log.e("loadTripDetails", "${e.message} ${e.stackTraceToString()}")
                    _state.value = TripListState(
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun onTripSelected(navController: NavController, trip: TripData) {
        // Handle trip selection
        navController.navigate("trip_detail_screen/${trip.tripNumber}")  // Assuming dynamic navigation
    }

    // Example function to generate payslip for a driver
    fun generatePayslipForDriver(
        driverId: Long,
        periodStart: String,
        periodEnd: String
    ): PayslipDto {
        // Simulate payslip generation
        return PayslipDto(driverId, periodStart, periodEnd)
    }
}
