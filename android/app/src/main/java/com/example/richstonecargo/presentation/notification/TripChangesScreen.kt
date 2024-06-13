package com.example.richstonecargo.presentation.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.domain.model.TripChange
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun parseAndFormatIsoDatetime(isoDatetime: String): String {
    // Step 1: Parse the ISO datetime string to LocalDateTime
    val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime = LocalDateTime.parse(isoDatetime, isoFormatter)

    // Step 2: Convert LocalDateTime to LocalDate
    val date = dateTime.toLocalDate()

    // Step 3: Format LocalDate to the desired pattern
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    return date.format(outputFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripChangesScreen(
    navController: NavController,
    viewModel: TripChangesViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val scrollState = rememberLazyListState()
    val tripChanges by viewModel.tripChanges.collectAsState()
    val notificationCount = tripChanges.size // Calculate notification count

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(navController = navController, isLoggedIn = true, notificationCount = notificationCount)
        },
        bottomBar = {
            CargoBottomBar(selectedRoute = "trip_changes_screen", navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0E21))
                .padding(innerPadding)
        ) {
            Column {
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
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text = "Изменение рейсов",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = scrollState, modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp)
                    ) {
                        items(tripChanges) { change ->
                            TripChangeItem(change)
                        }
                    }
                    CustomScrollbar(
                        scrollState = scrollState, modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 180.dp, top = 10.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
                        text = parseAndFormatIsoDatetime(change.time),
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
    thumbHeightFactor: Float = 0.26f
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
