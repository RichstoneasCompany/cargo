package com.example.richstonecargo.presentation.notification

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar

data class TripChange1(
    val title: String,
    val details: String,
    val time: String
)
@Composable
fun UpdateApplicationScreen(navController: NavController) {
    val primaryColor = Color(0xFF0A0E21)
    val scrollState = rememberLazyListState()

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
                selectedRoute = Screen.NotificationScreen.route,
                navController = navController
            )
        }
    ) { innerPadding ->
        val tripChanges = listOf(
            TripChange1(
                "Обновление версии приложения",
                "Вы установили последнюю версию приложения.",
                "10:00"
            )
        )

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
                        text = "Обновление приложения",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp)
                    ) {
                        items(tripChanges) { change ->
                            TripChangeItem2(change)
                        }
                    }
                    CustomScrollbar1(
                        scrollState = scrollState,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 180.dp, top = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TripChangeItem2(change: TripChange1) {
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
fun CustomScrollbar1(
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
