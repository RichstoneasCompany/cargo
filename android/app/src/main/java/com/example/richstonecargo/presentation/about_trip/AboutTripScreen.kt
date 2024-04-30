package com.example.richstonecargo.presentation.about_trip

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
fun AboutTripScreen(
    navController: NavController,
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val textColor = Color.White

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(
                navController = navController
            )
        },
        bottomBar = {
            CargoBottomBar(
                selectedRoute = Screen.TripListScreen.route,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
                .padding(innerPadding)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(80.dp))
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
                    Spacer(modifier = Modifier.width(220.dp))
                    // Title Text
                    Text(
                        "Информация о рейсе",
                        color = textColor,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(
                                top = 14.dp,
                                bottom = 2.dp
                            )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(26.dp)
                ) {
                    Spacer(Modifier.weight(0.05f))
                    Card(
                        backgroundColor = cardBackgroundColor,
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(end = 12.dp)
                            .height(400.dp)
                    ) {
                        Column {
                            Text(
                                "Номер рейса: CU-425",
                                color = textColor,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(
                                        top = 14.dp,
                                        bottom = 2.dp
                                    )
                            )

                            Row(
                                modifier = Modifier.padding(
                                    horizontal = 58.dp,
                                    vertical = 5.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(end = 16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "Точка погрузки",
                                        color = Color(0xFF0066FF),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    TimelineDotAbout(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "Точка разгрузки",
                                        color = Color(0xFF0066FF),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 16.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Spacer(modifier = Modifier.weight(0.5f))

                                    Text(
                                        "Хоргос",
                                        color = textColor,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.weight(0.5f))
                                    Text(
                                        "Дата погрузки: 01.03.2024",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                    Text("Время: 09:00", color = Color.Gray, fontSize = 14.sp)
                                    Text(
                                        "Расстояние: 331 км",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "Время в пути: 3ч. 37м.",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.weight(0.5f))
                                    Text(
                                        "Алматы",
                                        color = textColor,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(modifier = Modifier.weight(1f))
                                }


                            }
                        }
                    }

                    Spacer(Modifier.weight(0.02f))

                    // Card for Product Information
                    Card(
                        backgroundColor = cardBackgroundColor,
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(start = 12.dp)
                            .height(400.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    top = 14.dp,
                                    bottom = 30.dp,
                                    start = 30.dp,
                                    end = 30.dp
                                )
                        ) {
                            Text(
                                "Информация о товаре:",
                                color = textColor,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                            ProductInfoAbout("Наименование", "XXXXXX", textColor)
                            ProductInfoAbout("Описание", "XXXXXX", textColor)
                            ProductInfoAbout("Общий вес", "XXXXXX", textColor)
                            ProductInfoAbout("Количество паллетов", "XXXXXX", textColor)
                            ProductInfoAbout("Температура", "XXXXXX", textColor)
                        }
                    }
                    Spacer(Modifier.weight(0.05f))
                }
            }
        }
    }
}

@Composable
fun TimelineDotAbout(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.height(200.dp), onDraw = {
        val circleRadius = 12.dp.toPx()
        val strokeWidth = 4f
        val lineLength = size.height - (circleRadius * 2)

        drawCircle(
            color = Color.White,
            radius = circleRadius,
            center = Offset(x = size.width / 2, y = circleRadius)
        )

        drawLine(
            color = Color.White,
            start = Offset(x = size.width / 2, y = circleRadius * 2),
            end = Offset(x = size.width / 2, y = lineLength),
            strokeWidth = strokeWidth
        )

        drawCircle(
            color = Color.White,
            radius = circleRadius,
            center = Offset(x = size.width / 2, y = lineLength + circleRadius)
        )
    })
}


@Composable
fun ProductInfoAbout(title: String, detail: String, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = textColor, fontSize = 14.sp)
        Text(detail, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}