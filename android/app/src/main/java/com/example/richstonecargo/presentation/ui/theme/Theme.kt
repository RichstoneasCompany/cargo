package com.example.richstonecargo.presentation.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.richstonecargo.R


private val DarkColorPalette = darkColors(
    primary = ColorPrimary,
    background = DarkGray,
    onBackground = TextWhite,
    onPrimary = DarkGray
)

private val LightColorPalette = lightColors(
    primary = ColorPrimary,
    background = Color.White,
    onBackground = MediumGray,
    onPrimary = DarkGray
)
val PlayFairDisplayFontFamily = FontFamily(
    Font(R.font.playfairdisplay_medium, FontWeight.Medium)
)


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CargoAppTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    val colors = LightColorPalette
    val MontserratFontFamily = FontFamily(
        Font(R.font.montserrat_black, FontWeight.W900),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_italic, FontWeight.Thin),
    )
    val Typography = Typography(
        defaultFontFamily = MontserratFontFamily,
        body1 = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        h1 = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = PlayFairDisplayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        // Define other styles as needed
    )
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}