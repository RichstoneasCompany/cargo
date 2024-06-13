package com.example.richstonecargo.presentation.forgot_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.layout.CargoTopBarWithoutProfile
import com.example.richstonecargo.presentation.registration.OtpFields
import kotlinx.coroutines.delay


@Composable
fun SmsScreenForgot(
    navController: NavController,
    mobileNumber: String,
    viewModel: SmsScreenForgotViewModel = hiltViewModel(),
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var otpCode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val otpLength = 6 // Длина OTP кода

    val state by viewModel.state

    val navigationEvent by viewModel.navigationEvent
    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { route ->
            navController.navigate(route)
            viewModel.clearNavigationEvent()
        }
    }

    val context = LocalContext.current
    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    var isTimerRunning by remember { mutableStateOf(true) }
    var timerValue by remember { mutableStateOf(60) }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            timerValue = 59
            while (timerValue > 0) {
                delay(1000L)
                timerValue -= 1
            }
            isTimerRunning = false
        }
    }

    Scaffold(modifier = Modifier.background(primaryColor), topBar = {
        CargoTopBarWithoutProfile()
    }) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(primaryColor)
        ) {
            Card(
                backgroundColor = cardBackgroundColor,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(400.dp)
                    .padding(horizontal = 30.dp, vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Код подтверждения отправлен на номер",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = mobileNumber,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Введите 6-значный код", color = Color.White, fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Обновленный OtpFields
                        OtpFields(otpLength, otpCode) { code ->
                            if (code.length <= otpLength) {
                                otpCode = code
                                errorMessage = ""
                            } else {
                                errorMessage = "Код должен содержать 6 символов"
                            }
                        }
                    }

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            if (otpCode.length == otpLength) {
                                viewModel.verifyOtp(otpCode, mobileNumber)
                            } else {
                                errorMessage = "Код должен содержать 6 символов"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
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
                            Text(
                                text = "Подтвердить", color = Color.White, fontSize = 18.sp
                            )
                        }
                    }
                    if (isTimerRunning) {
                        TextButton(onClick = {}) {
                            Text(
                                text = "Запросить повторно через $timerValue с.",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        TextButton(onClick = {
                            otpCode = ""
                            forgotPasswordViewModel.sendOtp(mobileNumber)
                            isTimerRunning = true
                        }) {
                            Text(
                                text = "Получить код повторно",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OtpFields(otpLength: Int, otpCode: String, onCodeChange: (String) -> Unit) {
    Row {
        repeat(otpLength) { index ->
            OutlinedTextField(
                value = otpCode.getOrNull(index)?.toString() ?: "",
                onValueChange = {
                    if (it.length <= 1) {
                        val newCode = otpCode.toMutableList().apply {
                            if (it.isNotEmpty()) {
                                this[index] = it[0]
                            }
                        }.joinToString("")
                        onCodeChange(newCode)
                    }
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                singleLine = true,
                maxLines = 1,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

