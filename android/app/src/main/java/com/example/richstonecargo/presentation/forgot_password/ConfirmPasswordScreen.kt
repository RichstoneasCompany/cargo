package com.example.richstonecargo.presentation.forgot_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.layout.CargoTopBarWithoutProfile
import com.example.richstonecargo.presentation.layout.PasswordField

@Composable
fun ConfirmPasswordScreen(
    navController: NavController,
    mobileNumber: String,
    viewModel: ConfirmPasswordViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val passwordMatchError = remember { mutableStateOf(false) }
    val passwordLengthError = remember { mutableStateOf(false) }

    val state by viewModel.state
    val navigationEvent by viewModel.navigationEvent

    val context = LocalContext.current
    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { route ->
            navController.navigate(route)
            viewModel.clearNavigationEvent()
        }
    }

    Scaffold(modifier = Modifier.background(primaryColor), topBar = {
        CargoTopBarWithoutProfile()
    }) { innerPadding ->
        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .background(primaryColor)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Восстановление пароля", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mobileNumber,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(value = password.value, label = "Пароль", onValueChange = {
                password.value = it
                passwordMatchError.value = password.value != confirmPassword.value
                passwordLengthError.value = password.value.length < 8
            })
            if (passwordLengthError.value) {
                Text(
                    "Пароль должен содержать не менее 8 символов",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            } else {
                Text(
                    "Пароль должен содержать не менее 8 символов",
                    color = Color.Gray,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            PasswordField(
                value = confirmPassword.value,
                label = "Подтверждение пароля",
                onValueChange = {
                    confirmPassword.value = it
                    passwordMatchError.value = password.value != confirmPassword.value
                })
            if (passwordMatchError.value) {
                Text("Пароли не совпадают", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (password.value.length >= 8 && !passwordMatchError.value) {
                        viewModel.resetPassword(
                            newPassword = password.value,
                            confirmPassword = confirmPassword.value
                        )
                    } else {
                        if (password.value.length < 8) {
                            passwordLengthError.value = true
                        }
                        if (password.value != confirmPassword.value) {
                            passwordMatchError.value = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF))
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(18.dp),
                        strokeWidth = ProgressIndicatorDefaults.StrokeWidth
                    )
                } else {
                    Text("Восстановить пароль", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}
