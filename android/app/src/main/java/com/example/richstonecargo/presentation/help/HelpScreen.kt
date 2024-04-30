package com.example.richstonecargo.presentation.help

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar

@Composable
fun HelpScreen(
    navController: NavController,
    helpViewModel: HelpViewModel = viewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val textColor = Color.White
    val topics by helpViewModel.topics.observeAsState(initial = emptyList())


    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(navController = navController)
        },
        bottomBar = {
            CargoBottomBar(selectedRoute = Screen.HelpScreen.route, navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Помощь",
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val menuItems = listOf(
                    "Топ 10 вопросов",
                    "Регистрация и Авторизация",
                    "Рейсы и подготовка к рейсам",
                    "Зарплата",
                    "Действие в чрезвычайных ситуациях"
                )

                items(menuItems) { item ->
                    HelpMenuItem(item, cardBackgroundColor, textColor, navController)
                }
            }
        }
    }
}

@Composable
fun HelpMenuItem(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    navController: NavController
) {
    Card(
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                navController.navigate("help_detail_screen")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.notificationpageicon),
                contentDescription = "Далее",
                tint = textColor,
                modifier = Modifier.padding(end = 30.dp)
            )
        }
    }
}
