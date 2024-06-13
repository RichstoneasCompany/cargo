package com.example.richstonecargo.presentation.registration

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoTopBarWithoutProfile

@Composable
fun PasswordScreen(
    navController: NavController,
    mobileNumber: String,
    viewModel: PasswordScreenViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val passwordMatchError = remember { mutableStateOf(false) }

    val registrationState by viewModel.registrationState.observeAsState()
    LaunchedEffect(registrationState) {
        if (registrationState is Resource.Success) {
            navController.navigate(Screen.LoginScreen.route)
        }
    }

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = { CargoTopBarWithoutProfile() }
    ) { innerPadding ->
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
            Text("Регистрация", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mobileNumber,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(
                value = password.value,
                label = "Пароль",
                onValueChange = {
                    password.value = it
                    passwordMatchError.value = password.value != confirmPassword.value
                }
            )
            Text(
                "Пароль должен содержать не менее 8 символов",
                color = Color.Gray,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Confirm password input
            PasswordField(
                value = confirmPassword.value,
                label = "Подтверждение пароля",
                onValueChange = {
                    confirmPassword.value = it
                    passwordMatchError.value = password.value != confirmPassword.value
                }
            )

            if (passwordMatchError.value) {
                Text("Пароли не совпадают", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (password.value == confirmPassword.value) {
                        viewModel.registerUser(mobileNumber, password.value)
                    } else {
                        passwordMatchError.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
                enabled = registrationState !is Resource.Loading
            ) {

                if (registrationState is Resource.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(18.dp),
                        strokeWidth = ProgressIndicatorDefaults.StrokeWidth
                    )
                } else {
                    Text("Зарегистрироваться", color = Color.White, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("У вас уже есть аккаунт?", color = Color.Gray)
                TextButton(onClick = { navController.navigate(Screen.LoginScreen.route) }) {
                    Text("Войти", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun PasswordField(value: String, label: String, onValueChange: (String) -> Unit) {
    val glowColor = Color(0xFF5393F4) // The color for the glow effect
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.Transparent)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = screenWidth * 0.3f

            val desiredHeight = size.height * 0.05f

            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(glowColor.copy(alpha = 1f), Color.Transparent),
                    radius = maxOf(canvasWidth.value, desiredHeight) / 1.5f
                ),
                topLeft = Offset(
                    (size.width - canvasWidth.toPx()) / 2f,
                    (size.height - desiredHeight) / 1f
                ),
                size = Size(width = canvasWidth.toPx(), height = desiredHeight)
            )
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    label, color = Color.White, fontFamily = FontFamily(
                        Font(R.font.montserrat_regular)
                    )
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}
