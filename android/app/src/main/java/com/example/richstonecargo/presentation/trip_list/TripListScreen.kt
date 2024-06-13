package com.example.richstonecargo.presentation.trip_list

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.domain.model.TripListState
import com.example.richstonecargo.domain.model.TripStatus
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import com.example.richstonecargo.presentation.ui.theme.PlayFairDisplayFontFamily
import com.example.richstonecargo.presentation.util.DateComponent
import com.example.richstonecargo.presentation.util.extractDateComponent
import java.text.SimpleDateFormat
import java.util.Locale

val primaryColor = Color(0xFF0A0E21)
val textColor = Color.White
val highlightColor = Color(0xFF0066FF)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripListScreen(
    navController: NavController, viewModel: TripListViewModel = hiltViewModel()
) {
    val state by viewModel.state.observeAsState(TripListState())
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .background(primaryColor)
            .fillMaxSize(),
        topBar = { CargoTopBar(navController = navController) },
        bottomBar = {
            CargoBottomBar(
                selectedRoute = Screen.TripListScreen.route, navController = navController
            )
        }) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(primaryColor)
        ) {
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
            when {
                state.isLoading -> Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(primaryColor)
                ) {
                    CircularProgressIndicator(color = Color.White)
                }

                state.error.isNotEmpty() ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(primaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error, color = Color.Red
                        )
                    }

                state.trips.isNullOrEmpty() ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(primaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Нет данных о рейсах",
                            color = textColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                else -> {
                    Box {
                        LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
                            items(state.trips!!) { trip ->
                                TripCard(
                                    tripId = trip.id,
                                    tripNumber = trip.tripNumber,
                                    tripTime = trip.duration,
                                    loadingTime = SimpleDateFormat(
                                        "HH:mm dd.MM.yyyy", Locale.US
                                    ).format(trip.departureDate),
                                    textColor = textColor,
                                    isCurrentCargo = trip.status == TripStatus.IN_PROGRESS,
                                    highlightColor = highlightColor,
                                    navController = navController,
                                    tripDate = extractDateComponent(
                                        trip.departureDate, DateComponent.DAY
                                    ),
                                    tripMonth = extractDateComponent(
                                        trip.departureDate, DateComponent.MONTH_NAME
                                    ),
                                    tripRoute = trip.departurePlace,
                                    tripYear = extractDateComponent(
                                        trip.departureDate, DateComponent.YEAR
                                    ),
                                    onClick = { /* Navigation logic */ })
                            }
                        }
                        CustomScrollbar(
                            scrollState = scrollState,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 56.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TripCard(
    tripId: Long,
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
                        text = "Время погрузки: $loadingTime", color = textColor, fontSize = 14.sp
                    )
                    Text(
                        text = "Время в пути: $tripTime", color = textColor, fontSize = 14.sp
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
                    Text(text = "Подробнее >",
                        color = textColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable {
                            navController.navigate("about_trip_screen/${tripId}")
                        })
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}


@Composable
fun CustomScrollbar(
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    thumbColor: Color = Color.White,
    trackColor: Color = Color(0x55FFFFFF),
    thumbWidth: Dp = 12.dp,
    trackWidth: Dp = 12.dp,
    minThumbHeight: Dp = 48.dp,
    trackHeightFactor: Float = 1f,
    thumbHeightFactor: Float = 0.6f
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(thumbWidth)
            .padding(end = 8.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasHeight = size.height
            val canvasWidth = size.width

            val firstVisibleItemIndex = scrollState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = scrollState.firstVisibleItemScrollOffset
            val totalItemsCount = scrollState.layoutInfo.totalItemsCount
            val visibleItemsInfo = scrollState.layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isNotEmpty()) {
                val viewportHeight = scrollState.layoutInfo.viewportEndOffset.toFloat()
                val totalContentHeight = totalItemsCount * visibleItemsInfo[0].size.toFloat()

                val trackHeight = canvasHeight * trackHeightFactor

                val proportionVisible = viewportHeight / totalContentHeight
                val thumbHeightPx =
                    (canvasHeight * thumbHeightFactor * proportionVisible).coerceAtLeast(
                        minThumbHeight.toPx()
                    )

                val totalScrollRange = totalContentHeight - viewportHeight
                val currentScrollOffset =
                    (firstVisibleItemIndex * visibleItemsInfo[0].size + firstVisibleItemScrollOffset).toFloat()
                val scrollFraction = currentScrollOffset / totalScrollRange

                val thumbOffset = scrollFraction * (canvasHeight - thumbHeightPx)

                // Track
                drawRoundRect(
                    color = trackColor,
                    topLeft = Offset(x = (canvasWidth - trackWidth.toPx()) / 2, y = 0f),
                    size = Size(width = trackWidth.toPx(), height = trackHeight),
                    cornerRadius = CornerRadius(4.dp.toPx())
                )

                // Thumb
                drawRoundRect(
                    color = thumbColor,
                    topLeft = Offset(x = (canvasWidth - thumbWidth.toPx()) / 2, y = thumbOffset),
                    size = Size(width = thumbWidth.toPx(), height = thumbHeightPx),
                    cornerRadius = CornerRadius(4.dp.toPx())
                )
            }
        }
    }
}
