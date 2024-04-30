package com.example.richstonecargo.presentation.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.use_case.register_user.ReceiveRegistrationOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveRegistrationOtpViewModel @Inject constructor(
    private val receiveRegistrationOtpUseCase: ReceiveRegistrationOtpUseCase
) : ViewModel() {
    private val _registrationState = MutableLiveData<Resource<Unit>>()
    val registrationState: LiveData<Resource<Unit>> = _registrationState

    fun receiveOtp(mobileNumber: String) {
        _registrationState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                receiveRegistrationOtpUseCase(mobileNumber).collect { resource ->
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
