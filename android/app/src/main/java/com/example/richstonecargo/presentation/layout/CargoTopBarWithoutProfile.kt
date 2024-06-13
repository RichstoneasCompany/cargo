package com.example.richstonecargo.presentation.layout


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.richstonecargo.R


@Composable
fun CargoTopBarWithoutProfile(
) {
    TopAppBar(
        backgroundColor = Color(0xFF0A0E21),
        contentColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .weight(0.5f)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = "RICHSTONE CARGO",
                fontWeight = FontWeight.W900,
                color = Color.White,
                fontSize = 24.sp,
                letterSpacing = 2.sp,
                modifier = Modifier.weight(2f)
            )
        }
    }
}
