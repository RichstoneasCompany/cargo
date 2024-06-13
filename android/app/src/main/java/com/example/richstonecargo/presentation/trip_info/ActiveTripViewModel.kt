package com.example.richstonecargo.presentation.trip_info

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.ActiveTripState
import com.example.richstonecargo.domain.use_case.active_trip.ActiveTripUseCase
import com.example.richstonecargo.domain.use_case.finish_trip.FinishActiveTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class ActiveTripViewModel @Inject constructor(
    private val activeTripUseCase: ActiveTripUseCase,
    private val finishActiveTripUseCase: FinishActiveTripUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ActiveTripState())
    val state: State<ActiveTripState> = _state

    private val _startTripState = MutableLiveData<Boolean>()
    val startTripState: LiveData<Boolean> get() = _startTripState

    init {
        loadTripDetails()
    }

    fun finishActiveTrip() {
        viewModelScope.launch {
            _state.value.activeTrip?.let {
                finishActiveTripUseCase(it.id)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = ActiveTripState(isLoading = true)
                            }

                            is Resource.Success -> {
                                loadTripDetails()
                            }

                            is Resource.Error -> {
                                _state.value = ActiveTripState(
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }
                        }
                    }
                    .catch { e ->
                        Log.e("finishActiveTrip", "${e.message} ${e.stackTraceToString()}")
                        _state.value = ActiveTripState(
                            error = e.message ?: "An unexpected error occurred"
                        )
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

    private fun loadTripDetails() {
        viewModelScope.launch {
            activeTripUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = ActiveTripState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value = ActiveTripState(activeTrip = result.data)
                        }

                        is Resource.Error -> {
                            Log.e("activeTripUseCase", "${result.message}")
                            _state.value = ActiveTripState(
                                error = result.message ?: "An unexpected error occurred",
                                activeTrip = null
                            )
                        }
                    }
                }
                .catch { e ->
                    Log.e("activeTripUseCase catch", "${e.message} ${e.stackTraceToString()}")
                    if (e is HttpException && e.code() == 404) {
                        Log.e("activeTripUseCase catch", "404 error: ${e.message()}")
                        _state.value = ActiveTripState(
                            error = "Нет активных рейсов",
                            activeTrip = null
                        )
                    } else {
                        Log.e("activeTripUseCase catch", "${e.message} ${e.stackTraceToString()}")
                        _state.value = ActiveTripState(
                            error = e.message ?: "An unexpected error occurred",
                            activeTrip = null
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun startTrip() {
        _startTripState.value = true
    }
}
