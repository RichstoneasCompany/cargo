package com.example.richstonecargo.presentation.trip_info

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ActiveTripScreen(
    navController: NavController, viewModel: ActiveTripViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val textColor = Color.White

    val state = viewModel.state.value
    val context = LocalContext.current

    val startTripState by viewModel.startTripState.observeAsState(false)

    Scaffold(modifier = Modifier.background(primaryColor), topBar = {
        CargoTopBar(
            navController = navController
        )
    }, bottomBar = {
        CargoBottomBar(
            selectedRoute = Screen.ActiveTripScreen.route, navController = navController
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else if (state.error.isNotEmpty()) {
                Text(
                    state.error,
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(50.dp)
                )
            } else {
                AnimatedVisibility(
                    visible = state.activeTrip != null,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 1000))
                ) {
                    if (state.activeTrip == null) {
                        Text(
                            "Нет активных рейсов",
                            color = textColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(50.dp)
                        )
                    } else {
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
                                        "Номер рейса: ${state.activeTrip.tripNumber}",
                                        color = textColor,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(
                                                top = 14.dp, bottom = 2.dp
                                            )
                                    )

                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 58.dp, vertical = 5.dp
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
                                            TimelineDot(
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

                                            state.let {
                                                it.activeTrip?.let { it1 ->
                                                    Text(
                                                        it1.departurePlace,
                                                        color = textColor,
                                                        fontSize = 32.sp,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.weight(0.5f))
                                            Text(
                                                "Дата погрузки: ${
                                                    SimpleDateFormat(
                                                        "dd.MM.yyyy", Locale.US
                                                    ).format(state.activeTrip.departureDate)
                                                }", color = Color.Gray, fontSize = 14.sp
                                            )
                                            Text(
                                                "Время: ${
                                                    SimpleDateFormat(
                                                        "HH:mm", Locale.US
                                                    ).format(state.activeTrip.departureDate)
                                                }", color = Color.Gray, fontSize = 14.sp
                                            )
                                            Text(
                                                "Расстояние: ${state.activeTrip.distance}",
                                                color = Color.Gray,
                                                fontSize = 14.sp
                                            )
                                            Text(
                                                "Время в пути: ${state.activeTrip.duration}",
                                                color = Color.Gray,
                                                fontSize = 14.sp
                                            )
                                            Spacer(modifier = Modifier.weight(0.5f))
                                            state.let {
                                                it.activeTrip?.let { it1 ->
                                                    Text(
                                                        it1.arrivalPlace,
                                                        color = textColor,
                                                        fontSize = 32.sp,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.weight(0.02f))

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
                                            top = 14.dp, bottom = 30.dp, start = 30.dp, end = 30.dp
                                        )
                                ) {
                                    Text(
                                        "Информация о товаре:",
                                        color = textColor,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Spacer(modifier = Modifier.height(28.dp))
                                    state.let {
                                        it.activeTrip?.cargoInfo?.let { it1 ->
                                            ProductInfo(
                                                "Наименование", it1.name, textColor
                                            )
                                        }
                                    }
                                    state.let {
                                        it.activeTrip?.cargoInfo?.let { it1 ->
                                            ProductInfo(
                                                "Описание", it1.description, textColor
                                            )
                                        }
                                    }
                                    ProductInfo(
                                        "Общий вес",
                                        state.activeTrip.cargoInfo?.weight.toString(),
                                        textColor
                                    )
                                    ProductInfo(
                                        "Количество паллетов",
                                        state.activeTrip.cargoInfo?.numberOfPallets.toString(),
                                        textColor
                                    )
                                    ProductInfo(
                                        "Температура",
                                        state.activeTrip.cargoInfo?.temperature.toString(),
                                        textColor
                                    )
                                }
                            }
                            Spacer(Modifier.weight(0.05f))
                        }
                    }
                }
                if (state.activeTrip != null) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        if (!startTripState) {
                            Button(
                                onClick = { viewModel.startTrip() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF0A0E21
                                    )
                                ),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(3.dp, Color(0xFF0066FF)),
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .padding(horizontal = 30.dp),
                                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)
                            ) {
                                Text(
                                    "Начать рейс",
                                    color = Color(0xFF0066FF),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        } else {
                            Spacer(Modifier.weight(0.2f))
                            Button(
                                onClick = {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(state.activeTrip.routeLink)
                                    )
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF0A0E21
                                    )
                                ),
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
                                onClick = { viewModel.finishActiveTrip() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF0A0E21
                                    )
                                ),
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
        }
    }
}


@Composable
fun TimelineDot(modifier: Modifier = Modifier) {
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
    })
}


@Composable
fun ProductInfo(title: String, detail: String, textColor: Color) {
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
