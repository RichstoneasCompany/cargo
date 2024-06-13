package com.example.richstonecargo.presentation.forgot_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.layout.CargoTopBarWithoutProfile

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var mobileNumber by remember { mutableStateOf("+7") }
    var errorMessage by remember { mutableStateOf("") }
    val primaryColor = Color(0xFF0A0E21)

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

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBarWithoutProfile()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0E21))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(30.dp),
                    text = "Восстановление пароля",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { newValue ->
                        if (newValue.startsWith("+7") && newValue.length <= 12 && newValue.substring(
                                2
                            ).all { it.isDigit() }
                        ) {
                            mobileNumber = newValue
                            errorMessage = ""
                        } else if (newValue == "+7") {
                            mobileNumber = newValue
                            errorMessage = ""
                        } else {
                            errorMessage = "Неправильный формат номера"
                        }
                    },
                    label = { Text("Мобильный номер", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (mobileNumber.length == 12) {
                            viewModel.sendOtp(mobileNumber)
                        } else {
                            errorMessage = "Пожалуйста, введите корректный номер"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
                    modifier = Modifier.fillMaxWidth(0.45f),
                    shape = RoundedCornerShape(100)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(18.dp),
                            strokeWidth = ProgressIndicatorDefaults.StrokeWidth
                        )
                    } else {
                        Text(text = "Получить код по СМС", color = Color.White)
                    }
                }
            }
        }
    }
}
