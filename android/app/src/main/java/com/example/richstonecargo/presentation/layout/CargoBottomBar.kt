package com.example.richstonecargo.presentation.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen


@Composable
fun CargoBottomBar(selectedRoute: String, navController: NavController) {
    val widthMapping = mapOf(
        Screen.ActiveTripScreen.route to 1f,
        Screen.TripListScreen.route to 1f,
        Screen.CalculationListScreen.route to 1f,
        Screen.DispatcherScreen.route to 1f,
        Screen.HelpScreen.route to 0.3f
    )

    val nameMapping = mapOf(
        Screen.ActiveTripScreen.route to "Текущий рейс",
        Screen.TripListScreen.route to "Расписание рейсов",
        Screen.CalculationListScreen.route to "Расчетный лист",
        Screen.DispatcherScreen.route to "Связь с диспетчером",
        Screen.HelpScreen.route to "Помощь"
    )

    BottomAppBar(
        backgroundColor = Color(0xFF0A0E21),
        contentColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .size(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            nameMapping.forEach { (route, name) ->
                val isSelected = route == selectedRoute
                Card(
                    backgroundColor = if (isSelected) Color(0xFF0066FF) else Color(0xFF364156),
                    elevation = if (isSelected) 4.dp else 0.dp,
                    modifier = Modifier
                        .padding(4.dp, vertical = 10.dp)
                        .fillMaxHeight()
                        .weight(widthMapping[route] ?: 1f)
                        .clickable(onClick = { navController.navigate(route) })
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        if (name == "Помощь") {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.help),
                                    contentDescription = "Help Icon",
                                    modifier = Modifier.size(28.dp),
                                )
                                Text(
                                    text = name,
                                    fontSize = 8.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Text(
                                text = name,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}



