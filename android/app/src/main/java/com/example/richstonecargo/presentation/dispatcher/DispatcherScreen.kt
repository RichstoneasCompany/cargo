package com.example.richstonecargo.presentation.dispatcher

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar

@Composable
fun StylishButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.4f)
            .height(50.dp)
            .clip(RoundedCornerShape(24.dp))
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0066FF)),
        contentPadding = PaddingValues(),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(18.dp),
                strokeWidth = ProgressIndicatorDefaults.StrokeWidth
            )
        } else {
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DispatcherScreen(
    navController: NavController
) {
    var mobileNumber by remember { mutableStateOf("+7") }
    var password by remember { mutableStateOf("") }
    val primaryColor = Color(0xFF0A0E21)
    val isLoading = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = { CargoTopBar(navController, isLoggedIn = true) },
        bottomBar = {
            CargoBottomBar(
                selectedRoute = Screen.DispatcherScreen.route,
                navController = navController
            )
        }
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
                StylishButton(
                    text = "Открыть чат с диспетчером",
                    onClick = {
                        isLoading.value = true
                        val mapIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/+77064190996"))
                        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        ContextCompat.startActivity(navController.context, mapIntent, null)
                        isLoading.value = false
                    },
                    isLoading = isLoading.value
                )
            }
        }
    }
}
