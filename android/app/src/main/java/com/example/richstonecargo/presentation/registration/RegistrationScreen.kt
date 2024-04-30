package com.example.richstonecargo.presentation.registration

import android.widget.Toast
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.presentation.Screen

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: ReceiveRegistrationOtpViewModel = hiltViewModel()
) {
    var mobileNumber by remember { mutableStateOf("+7") }
    val registrationState by viewModel.registrationState.observeAsState()

    LaunchedEffect(registrationState) {
        if (registrationState is Resource.Success) {
            navController.navigate("${Screen.SmsScreen.route}/$mobileNumber")
        }
    }

    if (registrationState is Resource.Error) {
        val context = LocalContext.current
        Toast.makeText(context, (registrationState as Resource.Error).message, Toast.LENGTH_LONG)
            .show()
    }

    Scaffold(
        modifier = Modifier.background(Color(0xFF0A0E21)),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF0A0E21))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                RegistrationForm(mobileNumber, registrationState, onMobileNumberChange = {
                    mobileNumber = it
                }, onSendSmsClick = {
                    if (registrationState !is Resource.Loading) {
                        viewModel.receiveOtp(mobileNumber)
                    }
                })

                RegistrationAdditionalOptions(navController)
            }
        }
    }
}

@Composable
fun RegistrationForm(
    mobileNumber: String,
    registrationState: Resource<Unit>?,
    onMobileNumberChange: (String) -> Unit,
    onSendSmsClick: () -> Unit
) {
    Text("Регистрация", fontSize = 45.sp, fontWeight = FontWeight.Bold, color = Color.White)
    RegistrationMobileNumberField(value = mobileNumber, onValueChange = onMobileNumberChange)
    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onSendSmsClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
        modifier = Modifier.fillMaxWidth(0.45f),
        shape = RoundedCornerShape(100),
        enabled = registrationState !is Resource.Loading
    ) {
        if (registrationState is Resource.Loading) {
            CircularProgressIndicator(
                color = Color(0xFF0066FF),
                modifier = Modifier.size(18.dp),
                strokeWidth = ProgressIndicatorDefaults.StrokeWidth
            )
        } else {
            Text("Получить код по СМС", color = Color.White)
        }
    }
}

@Composable
fun RegistrationMobileNumberField(value: String, onValueChange: (String) -> Unit) {
    val glowColor = Color(0xFF5393F4)

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
            label = { Text("Мобильный номер", color = Color.White) },
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

@Composable
fun RegistrationAdditionalOptions(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("У вас уже есть аккаунт?", color = Color.Gray)
        TextButton(onClick = { navController.navigate(Screen.LoginScreen.route) }) {
            Text("Войти", color = Color.White)
        }
    }
}

