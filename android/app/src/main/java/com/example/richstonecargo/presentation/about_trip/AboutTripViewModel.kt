package com.example.richstonecargo.presentation.about_trip

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.data.remote.dto.ButtonState
import com.example.richstonecargo.domain.model.ActiveTripState
import com.example.richstonecargo.domain.use_case.about_trip.AboutTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AboutTripViewModel @Inject constructor(
    private val aboutTripUseCase: AboutTripUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ActiveTripState())
    val state: State<ActiveTripState> = _state

    private val _buttonState = MutableLiveData<ButtonState>()
    val buttonState: LiveData<ButtonState> = _buttonState

    fun loadTripDetails(tripId: Long) {
        viewModelScope.launch {
            aboutTripUseCase(tripId)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = ActiveTripState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value = ActiveTripState(activeTrip = result.data)
                        }

                        is Resource.Error -> {
                            _state.value = ActiveTripState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
                .catch { e ->
                    Log.e("aboutTripUseCase", "${e.message} ${e.stackTraceToString()}")
                    _state.value = ActiveTripState(
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}

