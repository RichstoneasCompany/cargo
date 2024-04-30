package com.example.richstonecargo.presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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

@Composable
fun NotificationScreen(navController: NavController) {
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
                selectedRoute = Screen.TripInfoScreen.route,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0E21))
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Text(
                "Уведомления",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn {
                items(
                    listOf(
                        NotificationItemData(
                            "Напоминания о рейсах", R.drawable.trip_reminders, true
                        ),
                        NotificationItemData("Изменение рейсов", R.drawable.trip_changes, false),
                        NotificationItemData(
                            "Обновление приложения",
                            R.drawable.updating_application,
                            false
                        )
                    )
                ) { itemData ->
                    NotificationItem(itemData = itemData, navController = navController)
                }
            }
        }
    }
}

data class NotificationItemData(val itemName: String, val iconId: Int, val hasSwitch: Boolean)

@Composable
fun NotificationItem(itemData: NotificationItemData, navController: NavController) {
    var switchedOn by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(130.dp)
                .padding(12.dp)
                .background(Color(0xFF364156), shape = RoundedCornerShape(25.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            Icon(
                painter = painterResource(id = itemData.iconId),
                contentDescription = itemData.itemName,
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.weight(0.05f))

            Text(
                text = itemData.itemName,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
            )
            if (itemData.hasSwitch) {
                CustomSwitch(
                    checked = switchedOn,
                    onCheckedChange = { switchedOn = it }
                )
                Spacer(modifier = Modifier.weight(0.1f))
            } else {
                IconButton(onClick = {
                    // Здесь используем `when` для вызова разных экранов на основе itemName
                    when (itemData.itemName) {
                        "Изменение рейсов" -> navController.navigate("trip_changes_screen")
                        "Обновление приложения" -> navController.navigate("update_application_screen")
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notificationpageicon),
                        contentDescription = "Действие",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.02f))
        }
    }
}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val borderColor = Color.White
    val thumbColor = Color.White
    val thumbPosition = if (checked) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        contentAlignment = thumbPosition,
        modifier = Modifier
            .width(50.dp)
            .height(20.dp)
            .clip(RoundedCornerShape(12.5.dp))
            .border(2.dp, borderColor, RoundedCornerShape(12.5.dp))
            .clickable { onCheckedChange(!checked) }
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(thumbColor, CircleShape)
        )
    }
}

