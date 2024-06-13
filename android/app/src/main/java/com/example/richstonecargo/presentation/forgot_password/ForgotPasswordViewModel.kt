package com.example.richstonecargo.presentation.forgot_password

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.ForgotPasswordSendOtpState
import com.example.richstonecargo.domain.use_case.forgot_password.ForgotPasswordUseCase
import com.example.richstonecargo.network.DynamicInterceptor
import com.example.richstonecargo.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ForgotPasswordSendOtpState())
    val state: State<ForgotPasswordSendOtpState> = _state

    private val _navigationEvent = mutableStateOf<String?>(null)
    val navigationEvent: State<String?> = _navigationEvent

    fun sendOtp(phoneNumber: String) {
        DynamicInterceptor.token = null
        viewModelScope.launch {
            forgotPasswordUseCase(phoneNumber)
                .onStart {
                    _state.value = ForgotPasswordSendOtpState(isLoading = true)
                }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = ForgotPasswordSendOtpState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value =
                                ForgotPasswordSendOtpState(forgotPasswordSendOtpResult = result.data)
                            _navigationEvent.value =
                                "${Screen.SmsScreenForgot.route}/${phoneNumber}"
                        }

                        is Resource.Error -> {
                            _state.value = ForgotPasswordSendOtpState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                            Log.d("LoginError", result.message ?: "An error occurred")
                        }
                    }
                }
                .onCompletion {
                    _state.value = _state.value.copy(isLoading = false)
                }
                .catch { e ->
                    _state.value =
                        ForgotPasswordSendOtpState(
                            error = e.message ?: "An unexpected error occurred"
                        )
                    Log.e("ForgotPasswordViewModel", "An error occurred: ${e.message}")
                }
                .launchIn(viewModelScope)
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}