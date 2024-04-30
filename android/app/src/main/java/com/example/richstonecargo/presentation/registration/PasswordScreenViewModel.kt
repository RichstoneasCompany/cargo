package com.example.richstonecargo.presentation.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.use_case.register_user.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordScreenViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _registrationState = MutableLiveData<Resource<Unit>>()
    val registrationState: LiveData<Resource<Unit>> = _registrationState

    fun registerUser(mobileNumber: String, password: String) {
        _registrationState.value = Resource.Loading()

        viewModelScope.launch {
            try {
                registerUserUseCase(mobileNumber, password).collect { resource ->
                    _registrationState.value = resource
                }
            } catch (e: Exception) {
                // Logging the exception can help in debugging the issue.
                Log.e("RegistrationVM", "Exception in registerUserUseCase: ${e.localizedMessage}")
                // If an exception occurs, update the LiveData to reflect the error state.
                _registrationState.value =
                    Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }
}
