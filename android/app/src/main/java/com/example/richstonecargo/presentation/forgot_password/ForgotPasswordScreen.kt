package com.example.richstonecargo.presentation.forgot_password

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
//    viewModel: RegistrationViewModel = hiltViewModel()
) {
    var mobileNumber by remember { mutableStateOf("+7") }
    val primaryColor = Color(0xFF0A0E21)
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0E21))
//            .padding(horizontal = 32.dp),
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
                        // Enforce the format +7 and only digits afterward, up to 12 characters
                        if (newValue.startsWith("+7") && newValue.length <= 12 && newValue.substring(
                                2
                            ).all { it.isDigit() }
                        ) {
                            mobileNumber = newValue
                        } else if (newValue == "+7") {
                            mobileNumber = newValue // Allow user to delete back to "+7"
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
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate(Screen.SmsScreenForgot.route) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
                    modifier = Modifier.fillMaxWidth(0.45f),
                    shape = RoundedCornerShape(100)
                ) {
                    Text(text = "Получить код по СМС", color = Color.White)
                }
            }
        }
    }
}

