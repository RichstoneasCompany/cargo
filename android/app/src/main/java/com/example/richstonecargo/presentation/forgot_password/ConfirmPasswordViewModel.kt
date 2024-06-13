package com.example.richstonecargo.presentation.forgot_password

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.ConfirmPasswordState
import com.example.richstonecargo.domain.use_case.forgot_password_reset.ForgotPasswordResetUseCase
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
class ConfirmPasswordViewModel @Inject constructor(
    private val forgotPasswordResetUseCase: ForgotPasswordResetUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ConfirmPasswordState())
    val state: State<ConfirmPasswordState> = _state

    private val _navigationEvent = mutableStateOf<String?>(null)
    val navigationEvent: State<String?> = _navigationEvent

    fun resetPassword(newPassword: String, confirmPassword: String) {
        viewModelScope.launch {
            forgotPasswordResetUseCase(newPassword = newPassword, confirmPassword = confirmPassword)
                .onStart {
                    _state.value = ConfirmPasswordState(isLoading = true)
                }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = ConfirmPasswordState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _navigationEvent.value =
                                Screen.LoginScreen.route
                        }

                        is Resource.Error -> {
                            _state.value = ConfirmPasswordState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                            Log.d("resetPassword", result.message ?: "An error occurred")
                        }
                    }
                }
                .onCompletion {
                    _state.value = _state.value.copy(isLoading = false)
                }
                .catch { e ->
                    _state.value =
                        ConfirmPasswordState(
                            error = e.message ?: "An unexpected error occurred"
                        )
                    Log.e("ConfirmPasswordViewModel", "An error occurred: ${e.message}")
                }
                .launchIn(viewModelScope)
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}