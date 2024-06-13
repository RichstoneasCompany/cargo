package com.example.richstonecargo.presentation.registration

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoTopBarWithoutProfile


@Composable
fun SmsScreen(
    navController: NavController,
    viewModel: SendRegistrationOtpViewModel = hiltViewModel(),
    mobileNumber: String
) {
    val focusManager = LocalFocusManager.current
    var otpCode by remember { mutableStateOf("") }
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val otpLength = 6 // Длина OTP кода

    val registrationState by viewModel.registrationState.observeAsState()

    LaunchedEffect(registrationState) {
        if (registrationState is Resource.Success) {
            navController.navigate("${Screen.PasswordScreen.route}/$mobileNumber")
        }
    }

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = { CargoTopBarWithoutProfile() }
    ) { innerPadding ->
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
                        text = "Введите 6-значный код",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        OtpFields(otpLength, otpCode) { code ->
                            otpCode = code
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            viewModel.sendOtp(otpCode)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(50.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
                        enabled = registrationState !is Resource.Loading

                    ) {

                        if (registrationState is Resource.Loading) {
                            CircularProgressIndicator(
                                color = Color(0xFF0066FF),
                                modifier = Modifier.size(18.dp),
                                strokeWidth = ProgressIndicatorDefaults.StrokeWidth
                            )
                        } else {
                            Text(
                                text = "Подтвердить",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                    TextButton(
                        onClick = {
                            otpCode = ""
                        }
                    ) {
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

@Composable
fun OtpFields(length: Int, code: String, onCodeChanged: (String) -> Unit) {
    val focusRequesters = List(length) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        (0 until length).forEach { index ->
            val isFocused = index == code.length

            OutlinedTextField(
                value = if (index < code.length) code[index].toString() else "",
                onValueChange = { value ->
                    if (value.length <= 1) {
                        val newCode = buildString {
                            // Safely take substring only if the index is within the current length
                            append(code.take(index))
                            if (value.isNotEmpty()) append(value)
                            // Append the remaining part only if it's within the bounds
                            if (index + 1 <= code.length) append(code.substring(index + 1))
                        }.filter { it.isDigit() }
                        onCodeChanged(newCode)
                        if (value.isNotEmpty() && index < length - 1) {
                            focusRequesters.getOrNull(index + 1)?.requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .size(45.dp, 55.dp)
                    .focusRequester(focusRequesters[index]),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    cursorColor = Color.White
                )
            )
        }
    }
}

