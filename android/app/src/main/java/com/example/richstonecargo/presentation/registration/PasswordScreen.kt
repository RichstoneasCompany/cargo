package com.example.richstonecargo.presentation.registration

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.presentation.Screen

@Composable
fun PasswordScreen(
    navController: NavController,
    mobileNumber: String,
    viewModel: PasswordScreenViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)

    val registrationState by viewModel.registrationState.observeAsState()
    LaunchedEffect(registrationState) {
        if (registrationState is Resource.Success) {
            navController.navigate(Screen.LoginScreen.route)
        }
    }

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            TopAppBar(
                backgroundColor = primaryColor,
                contentColor = Color.White,
                elevation = 0.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Cargo Logo",
                        modifier = Modifier.size(150.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Give some space between the logo and text
                    Text(
                        text = "RICHSTONE CARGO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }
        }
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
                onValueChange = { password.value = it }
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
                onValueChange = { confirmPassword.value = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.registerUser(mobileNumber, password.value)
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

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.Transparent)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.width
            val desiredHeight = size.height * 0.05f

            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(glowColor.copy(alpha = 1f), Color.Transparent),

                    radius = maxOf(
                        canvasWidth
                    ) / 1.5f
                ),
                topLeft = Offset(0f, (size.height - desiredHeight) / 1),
                size = Size(width = canvasWidth, height = desiredHeight)
            )
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = Color.White) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
        )
    }
}
