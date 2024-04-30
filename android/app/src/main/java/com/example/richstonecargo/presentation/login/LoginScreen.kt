package com.example.richstonecargo.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.data.remote.dto.LoginUserState
import com.example.richstonecargo.global.NavigationCommand
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoTopBar


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var mobileNumber by remember { mutableStateOf("+7") }
    var password by remember { mutableStateOf("") }
    val primaryColor = Color(0xFF0A0E21)
    val onForgotPasswordClick = {
        navController.navigate(Screen.ForgotPasswordScreen.route)
    }

    val onLoginClick = {
        viewModel.login(mobileNumber, password)
    }

    val state = viewModel.state.value
    val context = LocalContext.current
    state.error.let { error ->
        if (error.isNotEmpty()) {
            LaunchedEffect(error) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    val commands by viewModel.navigationCommands.observeAsState()
    LaunchedEffect(commands) {
        commands?.getContentIfNotHandled()?.let { command ->
            when (command) {
                is NavigationCommand.ToDestination -> navController.navigate(command.route)
                NavigationCommand.Back -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = { CargoTopBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(primaryColor),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                AuthorizationForm(
                    mobileNumber = mobileNumber,
                    onMobileNumberChange = { mobileNumber = it },
                    password = password,
                    onPasswordChange = { password = it },
                    onLoginClick = onLoginClick,
                    onForgotPasswordClick = onForgotPasswordClick,
                    state = state
                )
                Spacer(modifier = Modifier.height(20.dp))
                AdditionalOptions(navController)
            }
        }
        QuestionIconWithCircleClickable(
            onClick = { navController.navigate(Screen.HelpScreen.route) },
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun AuthorizationForm(
    mobileNumber: String,
    onMobileNumberChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    state: LoginUserState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Авторизация",
            color = Color.White,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(24.dp))
        MobileNumberField(mobileNumber, onMobileNumberChange)
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(password, onPasswordChange)
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
            TextButton(
                onClick = onForgotPasswordClick, modifier = Modifier.padding(start = 155.dp)
            ) {
                Text("Забыли пароль?", color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LoginButton(onClick = { onLoginClick() }, isLoading = state.isLoading)
    }
}

@Composable
fun MobileNumberField(value: String, onValueChange: (String) -> Unit) {
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
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                "Пароль",
                color = Color.White,
                fontFamily = FontFamily(
                    Font(R.font.montserrat_regular)
                ),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoginButton(isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
        modifier = Modifier.fillMaxWidth(0.2f),
        shape = RoundedCornerShape(100),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF0066FF),
                modifier = Modifier.size(18.dp),
                strokeWidth = ProgressIndicatorDefaults.StrokeWidth
            )
        } else {
            Text(text = "Войти", color = Color.White)
        }
    }
}

@Composable
fun AdditionalOptions(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("У вас нет аккаунта?", color = Color.Gray)
        Spacer(Modifier.width(8.dp))
        TextButton(onClick = { navController.navigate(Screen.RegistrationScreen.route) }) {
            Text("Зарегистрируйтесь", color = Color.White)
        }
    }
}

@Composable
fun QuestionIconWithCircleClickable(
    modifier: Modifier = Modifier,
    iconSize: Dp = 34.dp,
    circleColor: Color = Color.White,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(iconSize * 2)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = circleColor,
                radius = iconSize.toPx() / 1,
                center = Offset(size.width / 2, size.height / 2)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.help),
            contentDescription = "Question",
            tint = Color(0xFF0A0E21),
            modifier = Modifier.size(iconSize)
        )
    }
}
