package com.example.richstonecargo.presentation.trip_list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import com.example.richstonecargo.presentation.ui.theme.PlayFairDisplayFontFamily


@Composable
fun TripListScreen(
    navController: NavController
) {
    val primaryColor = Color(0xFF0A0E21)
    val textColor = Color.White
    val highlightColor = Color(0xFF0066FF)
    val tripsData = listOf(
        TripData(
            date = "01",
            year = "2024",
            month = "МАРТ",
            tripNumber = "CU-425",
            route = "Алматы-Хоргос",
            loadingTime = "09:00",
            tripTime = "3ч. 37м.",
            isCurrentCargo = false
        ),
        // ... add four more TripData instances with different data
        TripData(
            date = "02",
            year = "2024",
            month = "МАРТ",
            tripNumber = "CU-426",
            route = "Нур-Султан-Хоргос",
            loadingTime = "10:00",
            tripTime = "4ч. 00м.",
            isCurrentCargo = true
        ),
        TripData(
            date = "03",
            year = "2024",
            month = "МАРТ",
            tripNumber = "CU-427",
            route = "Шымкент-Хоргос",
            loadingTime = "11:00",
            tripTime = "2ч. 30м.",
            isCurrentCargo = false
        ),
        TripData(
            date = "04",
            year = "2024",
            month = "МАРТ",
            tripNumber = "CU-428",
            route = "Атырау-Хоргос",
            loadingTime = "12:00",
            tripTime = "5ч. 15м.",
            isCurrentCargo = false
        ),
        TripData(
            date = "05",
            year = "2024",
            month = "МАРТ",
            tripNumber = "CU-429",
            route = "Костанай-Хоргос",
            loadingTime = "13:00",
            tripTime = "3ч. 45м.",
            isCurrentCargo = false
        )
    )
    val scrollState = rememberLazyListState()
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
    )
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(primaryColor)
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Расписание рейсов",
                        color = textColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
                items(tripsData) { trip ->
                    TripCard(
                        tripDate = trip.date,
                        tripYear = trip.year,
                        tripMonth = trip.month,
                        tripNumber = trip.tripNumber,
                        tripRoute = trip.route,
                        loadingTime = trip.loadingTime,
                        tripTime = trip.tripTime,
                        textColor = textColor,
                        highlightColor = highlightColor,
                        onClick = {
                            // TODO: Handle navigation to the trip details
                        },
                        navController = navController,
                        isCurrentCargo = trip.isCurrentCargo
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
            CustomScrollbar(
                scrollState = scrollState,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 56.dp, top = 78.dp)
            )
        }
    }
}

@Composable
fun TripCard(
    tripDate: String,
    tripYear: String,
    tripMonth: String,
    tripNumber: String,
    tripRoute: String,
    loadingTime: String,
    tripTime: String,
    textColor: Color,
    highlightColor: Color,
    onClick: () -> Unit,
    navController: NavController,
    isCurrentCargo: Boolean

) {
    val cardBackgroundColor = Color(0xFF364156)
    val borderColor = if (isCurrentCargo) Color(0xFF0066FF) else Color.Transparent
    val borderWidth = if (isCurrentCargo) 8.dp else 0.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = if (isCurrentCargo) 14.dp else 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            backgroundColor = cardBackgroundColor,
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(150.dp)
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(50.dp))
        ) {
            if (isCurrentCargo) {
                Text(
                    text = "Текущий рейс",
                    color = Color(0xFF0066FF),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 50.dp, top = 14.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .padding(start = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tripDate,
                    color = textColor,
                    fontSize = 74.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PlayFairDisplayFontFamily,
                    modifier = Modifier.align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "$tripYear\n$tripMonth",
                    color = textColor,
                    fontSize = 32.sp,
                    fontFamily = PlayFairDisplayFontFamily,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.weight(0.5f))
                Column {
                    Text(
                        text = "Номер рейса: $tripNumber",
                        color = textColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Время погрузки: $loadingTime",
                        color = textColor,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Время в пути: $tripTime",
                        color = textColor,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.weight(0.5f))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = tripRoute,
                        color = textColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Подробнее >",
                        color = textColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable {
                            navController.navigate("about_trip_screen")
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}


data class TripData(
    val date: String,
    val year: String,
    val month: String,
    val tripNumber: String,
    val route: String,
    val loadingTime: String,
    val tripTime: String,
    val isCurrentCargo: Boolean
)

@Composable
fun CustomScrollbar(
    scrollState: LazyListState,
    modifier: Modifier,
    thumbColor: Color = Color.White,
    trackColor: Color = Color(0x55FFFFFF),
    thumbWidth: Dp = 12.dp,
    thumbHeight: Dp = 70.dp,
    trackWidth: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(thumbWidth)
            .padding(top = 48.dp, end = 8.dp, bottom = 48.dp)
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            val thumbHeightPx = thumbHeight.toPx()

            // трек ползунка
            drawRect(
                color = trackColor,
                topLeft = Offset(x = (canvasWidth - trackWidth.toPx()) / 2, y = 0f),
                size = Size(width = trackWidth.toPx(), height = canvasHeight)
            )

            // Расчет позиции ползунка
            val totalScrollRange = scrollState.layoutInfo.totalItemsCount - 1
            val currentScrollFraction = if (totalScrollRange != 0) {
                scrollState.firstVisibleItemIndex.toFloat() / totalScrollRange
            } else 0f
            val thumbTop = currentScrollFraction * (canvasHeight - thumbHeightPx)

            // ползунок
            drawRoundRect(
                color = thumbColor,
                topLeft = Offset(x = (canvasWidth - thumbWidth.toPx()) / 2, y = thumbTop),
                size = Size(width = thumbWidth.toPx(), height = thumbHeightPx),
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }
    }
}

