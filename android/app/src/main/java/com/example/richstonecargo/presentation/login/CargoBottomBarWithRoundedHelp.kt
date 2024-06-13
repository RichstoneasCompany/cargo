package com.example.richstonecargo.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.richstonecargo.presentation.Screen


@Composable
fun CargoBottomBarWithRoundedHelp(
    navController: NavController
) {


    BottomAppBar(
        backgroundColor = Color(0xFF0A0E21),
        contentColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .size(100.dp)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuestionIconWithCircleClickable(
                onClick = { navController.navigate(Screen.HelpScreenWithoutAuthorization.route) },
                modifier = Modifier.padding(start = 750.dp)
            )
        }
    }
}



