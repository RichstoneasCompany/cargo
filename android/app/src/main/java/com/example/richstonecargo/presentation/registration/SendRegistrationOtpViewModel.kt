package com.example.richstonecargo.presentation.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.use_case.register_user.SendRegistrationOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendRegistrationOtpViewModel @Inject constructor(
    private val sendRegistrationOtpUseCase: SendRegistrationOtpUseCase
) : ViewModel() {
    private val _registrationState = MutableLiveData<Resource<Unit>>()
    val registrationState: LiveData<Resource<Unit>> = _registrationState

    fun sendOtp(mobileNumber: String, otp: String) {
        _registrationState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                sendRegistrationOtpUseCase(mobileNumber, otp).collect { resource ->
                    _registrationState.value = resource
                }
            } catch (e: Exception) {
                // Logging the exception can help in debugging the issue.
                Log.e("RegistrationVM", "Exception in receiveOtp: ${e.localizedMessage}")
                // If an exception occurs, update the LiveData to reflect the error state.
                _registrationState.value =
                    Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }
}
