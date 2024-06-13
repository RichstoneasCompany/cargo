package com.example.richstonecargo.presentation.forgot_password

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.ForgotPasswordVerifyOtpState
import com.example.richstonecargo.domain.use_case.forgot_password_verify_otp.ForgotPasswordVerifyOtpUseCase
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
class SmsScreenForgotViewModel @Inject constructor(
    private val forgotPasswordVerifyOtpUseCase: ForgotPasswordVerifyOtpUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ForgotPasswordVerifyOtpState())
    val state: State<ForgotPasswordVerifyOtpState> = _state

    private val _navigationEvent = mutableStateOf<String?>(null)
    val navigationEvent: State<String?> = _navigationEvent

    fun verifyOtp(otp: String, phoneNumber: String) {
        DynamicInterceptor.token = null
        viewModelScope.launch {
            forgotPasswordVerifyOtpUseCase(otp)
                .onStart {
                    _state.value = ForgotPasswordVerifyOtpState(isLoading = true)
                }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = ForgotPasswordVerifyOtpState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value =
                                ForgotPasswordVerifyOtpState(forgotPasswordVerifyOtpResult = result.data)
                            if (result.data?.access_token == null) throw Exception("no access token found")
                            DynamicInterceptor.token = result.data.access_token
                            _navigationEvent.value =
                                "${Screen.ConfirmPasswordScreen.route}/${phoneNumber}"
                        }

                        is Resource.Error -> {
                            _state.value = ForgotPasswordVerifyOtpState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                            Log.d("verifyOtp", result.message ?: "An error occurred")
                        }
                    }
                }
                .onCompletion {
                    _state.value = _state.value.copy(isLoading = false)
                }
                .catch { e ->
                    _state.value =
                        ForgotPasswordVerifyOtpState(
                            error = e.message ?: "An unexpected error occurred"
                        )
                    Log.e("SmsScreenForgotViewModel", "An error occurred: ${e.message}")
                }
                .launchIn(viewModelScope)
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}