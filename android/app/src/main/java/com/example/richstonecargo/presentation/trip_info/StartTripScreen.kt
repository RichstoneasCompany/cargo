package com.example.richstonecargo.presentation.trip_info

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import com.example.richstonecargo.presentation.registration.TripInfoViewModel

@Composable
fun StartTripScreen(
    navController: NavController,
    tripInfoViewModel: TripInfoViewModel = viewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val textColor = Color.White
    val trip by tripInfoViewModel.trip.observeAsState()
    val product by tripInfoViewModel.product.observeAsState()

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(
                navController = navController
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
                .background(primaryColor)
                .padding(innerPadding)
        ) {
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
                            "Номер рейса: ${trip?.tripNumber}",
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

                        Row(modifier = Modifier.padding(horizontal = 58.dp, vertical = 5.dp)) {
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
                                TimelineDot2(
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

                                trip?.let {
                                    Text(
                                        it.loadPoint,
                                        color = textColor,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.weight(0.5f))
                                Text(
                                    "Дата погрузки: ${trip?.loadDate}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Text(
                                    "Время: ${trip?.loadTime}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Text(
                                    "Расстояние: ${trip?.distance}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Text(
                                    "Время в пути: ${trip?.tripTime}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.weight(0.5f))
                                trip?.let {
                                    Text(
                                        it.unloadPoint,
                                        color = textColor,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
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
                            .padding(top = 14.dp, bottom = 30.dp, start = 30.dp, end = 30.dp)
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
                        product?.let { ProductInfo("Наименование", it.name, textColor) }
                        product?.let { ProductInfo("Описание", it.description, textColor) }
                        product?.let { ProductInfo("Общий вес", it.totalWeight, textColor) }
                        product?.let {
                            ProductInfo(
                                "Количество паллетов",
                                it.palletCount,
                                textColor
                            )
                        }
                        product?.let { ProductInfo("Температура", it.temperature, textColor) }
                    }
                }
                Spacer(Modifier.weight(0.05f))
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Spacer(Modifier.weight(0.2f))
                Button(
                    onClick = { tripInfoViewModel.onBuildRouteClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0A0E21)),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(3.dp, Color(0xFF0066FF)),
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .padding(horizontal = 30.dp),
                    elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        "Построить маршрут",
                        color = Color(0xFF0066FF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.weight(0.3f))
                Button(
                    onClick = { tripInfoViewModel.onEndTripClick(navController) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0A0E21)),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(3.dp, Color.Red),
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(horizontal = 30.dp),
                    elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        "Завершить рейс",
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.weight(0.2f))
            }
        }
    }
}

@Composable
fun TimelineDot2(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.height(200.dp), onDraw = {
        val circleRadius = 12.dp.toPx()
        val strokeWidth = 4f
        val firstHalfLineEnd = size.height / 2
        val secondHalfLineStart = size.height / 2

        drawCircle(
            color = Color(0xFF0066FF),
            radius = circleRadius,
            center = Offset(x = size.width / 2, y = circleRadius)
        )
        drawLine(
            color = Color(0xFF0066FF),
            start = Offset(x = size.width / 2, y = circleRadius * 2),
            end = Offset(x = size.width / 2, y = firstHalfLineEnd),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = Color.White,
            start = Offset(x = size.width / 2, y = secondHalfLineStart),
            end = Offset(x = size.width / 2, y = size.height - circleRadius),
            strokeWidth = strokeWidth
        )
        drawCircle(
            color = Color.White,
            radius = circleRadius,
            center = Offset(x = size.width / 2, y = size.height - circleRadius)
        )
    }
    )
}


@Composable
fun ProductInfo1(title: String, detail: String, textColor: Color) {
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
