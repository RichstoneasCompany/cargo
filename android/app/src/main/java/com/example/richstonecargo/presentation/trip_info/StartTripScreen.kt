package com.example.richstonecargo.presentation.trip_info

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StartTripScreen1(
    navController: NavController
) {
}

@Composable
fun TimelineDot11(modifier: Modifier = Modifier) {
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
