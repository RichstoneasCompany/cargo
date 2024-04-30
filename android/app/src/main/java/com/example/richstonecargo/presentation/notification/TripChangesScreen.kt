package com.example.richstonecargo.presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar


data class TripChange(
    val title: String,
    val details: String,
    val time: String
)

@Composable
fun TripChangesScreen(navController: NavController) {
    val primaryColor = Color(0xFF0A0E21)

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(
                navController = navController,
                isLoggedIn = true
            )
        },
        bottomBar = {
            CargoBottomBar(
                selectedRoute = Screen.HelpScreen.route,
                navController = navController
            )
        }
    ) { innerPadding ->
        val tripChanges = listOf(
            TripChange(
                "Изменение во времени",
                "Рейс CU-425 Харьков-Алматы 01.04.2024 в 09:00 перенесен на 18:00.",
                "10:35"
            ),
            TripChange(
                "Изменение во времени",
                "Рейс CU-425 Харьков-Алматы 01.04.2024 в 09:00 перенесен на 18:00.",
                "10:35"
            ),
            TripChange(
                "Изменение во времени",
                "Рейс CU-425 Харьков-Алматы 01.04.2024 в 09:00 перенесен на 18:00.",
                "10:35"
            ),
            TripChange(
                "Изменение во времени",
                "Рейс CU-425 Харьков-Алматы 01.04.2024 в 09:00 перенесен на 18:00.",
                "10:35"
            ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0E21))
                .padding(innerPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(180.dp))
                // BackButton
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Назад",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text = "Назад",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(150.dp))
                Text(
                    text = "Изменение рейсов",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(tripChanges) { change ->
                    TripChangeItem(change)
                }
            }
        }
    }
}

@Composable
fun TripChangeItem(change: TripChange) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color(0xFF364156),
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = change.time,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0066FF))
                    )
                }
                Text(
                    text = change.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = change.details,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}