package com.example.richstonecargo.presentation.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.data.remote.dto.ButtonState
import com.example.richstonecargo.domain.use_case.profile_details.ProfileDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDetailsUseCase: ProfileDetailsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ProfileDetailsState())
    val state: State<ProfileDetailsState> = _state

    private val _buttonState = MutableLiveData<ButtonState>()
    val buttonState: LiveData<ButtonState> = _buttonState

    init {
        loadUserDetails()
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            profileDetailsUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = ProfileDetailsState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value = ProfileDetailsState(profileDetails = result.data)
                        }

                        is Resource.Error -> {
                            _state.value = ProfileDetailsState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
                .catch { e ->
                    Log.e("loadTripDetails", "${e.message} ${e.stackTraceToString()}")
                    _state.value = ProfileDetailsState(
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}
