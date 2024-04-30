package com.example.richstonecargo.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.data.remote.dto.LoginUserState
import com.example.richstonecargo.domain.use_case.login_user.LoginUserUseCase
import com.example.richstonecargo.global.Event
import com.example.richstonecargo.global.NavigationCommand
import com.example.richstonecargo.global.NavigationManager
import com.example.richstonecargo.global.UserInfoManager
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
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val navigationManager: NavigationManager

) : ViewModel() {
    private val _state = mutableStateOf(LoginUserState())
    val state: State<LoginUserState> = _state

    val navigationCommands: LiveData<Event<NavigationCommand>> = navigationManager.commands

    fun login(phone: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase(phone, password)
                .onStart {
                    _state.value = LoginUserState(isLoading = true)
                }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = LoginUserState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value = LoginUserState(userInfo = result.data)
                            if (result.data?.access_token == null) throw Exception("no access token found")
                            DynamicInterceptor.token = "new_access_token"
                            UserInfoManager.setUserInfo(result.data)
                            Log.d("LoginSuccess", "User logged in successfully.")
                            navigationManager.navigate(NavigationCommand.ToDestination(Screen.TripInfoScreen.route))
                        }

                        is Resource.Error -> {
                            _state.value = LoginUserState(
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
                        LoginUserState(error = e.message ?: "An unexpected error occurred")
                    Log.e("LoginError", "An error occurred: ${e.message}")
                }
                .launchIn(viewModelScope)
        }
    }
}
