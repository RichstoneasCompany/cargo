package com.example.richstonecargo.presentation.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.richstonecargo.R

@Composable
fun PasswordField(value: String, label: String, onValueChange: (String) -> Unit) {
    val glowColor = Color(0xFF5393F4) // The color for the glow effect
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.Transparent)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = screenWidth * 0.3f

            val desiredHeight = size.height * 0.05f

            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(glowColor.copy(alpha = 1f), Color.Transparent),
                    radius = maxOf(canvasWidth.value, desiredHeight) / 1.5f
                ),
                topLeft = Offset(
                    (size.width - canvasWidth.toPx()) / 2f,
                    (size.height - desiredHeight) / 1f
                ),
                size = Size(width = canvasWidth.toPx(), height = desiredHeight)
            )
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    label, color = Color.White, fontFamily = FontFamily(
                        Font(R.font.montserrat_regular)
                    )
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}
