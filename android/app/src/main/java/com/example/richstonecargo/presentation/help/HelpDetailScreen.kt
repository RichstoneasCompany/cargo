package com.example.richstonecargo.presentation.help

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.domain.model.Question
import com.example.richstonecargo.domain.model.QuestionsState
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar

@Composable
fun HelpDetailScreen(
    navController: NavController,
    topicId: Long,
    questionsViewModel: QuestionsViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val textColor = Color.White
    val state by questionsViewModel.state.observeAsState(QuestionsState())
    val scrollState = rememberLazyListState()
    LaunchedEffect(topicId) {
        questionsViewModel.loadQuestionsDetail(topicId)
    }

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(navController = navController)
        },
        bottomBar = {
            CargoBottomBar(selectedRoute = Screen.HelpScreen.route, navController = navController)
        }
    ) { innerPadding ->
        Box(
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
                    Spacer(modifier = Modifier.weight(0.5f))
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
                    Spacer(modifier = Modifier.weight(1.2f))
                    Text(
                        text = state.topicName,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1.8f))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(primaryColor)
                ) {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White
                            )
                        }

                        state.error.isNotEmpty() -> {
                            Log.e("HelpDetailScreen", "Error: ${state.error}")
                            Text(
                                text = state.error,
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        else -> {
                            Log.d("HelpDetailScreen", "Questions: ${state.questions}")
                            LazyColumn(
                                state = scrollState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(state.questions) { question ->
                                    HelpCard(question, cardBackgroundColor, textColor)
                                }
                            }
                        }
                    }
                }
            }
            CustomScrollbar(
                scrollState = scrollState,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 60.dp, top = 100.dp)
            )
        }
    }
}

@Composable
fun HelpCard(question: Question, cardBackgroundColor: Color, textColor: Color) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Card(
        backgroundColor = cardBackgroundColor,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                question.question?.let {
                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(700.dp)
                    )
                }
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .size(30.dp)
                        .graphicsLayer { rotationZ = rotationAngle }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dropdownicon),
                        contentDescription = if (expanded) "Show less" else "Show more",
                        tint = Color(0xFF0066FF)
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    question.answer?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = textColor,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
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
    thumbHeightFactor: Float = 0.3f
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
