package com.example.richstonecargo.presentation.calculation_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar

@Composable
fun TripSalaryScreen(
    navController: NavController
) {
    val primaryColor = Color(0xFF0A0E21)


    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(navController = navController)
        },
        bottomBar = {

            CargoBottomBar(
                selectedRoute = Screen.CalculationListScreen.route,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(primaryColor),
            contentAlignment = Alignment.Center
        ) {
            LargeCard2(navController = navController)
        }
    }
}

@Composable
fun LargeCard2(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 180.dp)
            .heightIn(min = 250.dp, max = 450.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF364156)
    ) {
        ContentCard2(navController = navController)
    }
}

@Composable
fun ContentCard2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(40.dp))
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
            Spacer(modifier = Modifier.width(120.dp))
            // Title Text
            Text(
                text = "Оклад по рейсам",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(Modifier.weight(1f))
        SalaryItem2(label = "03.03.2024-09.03.2024", value = "50000 тг")
        SalaryItem2(label = "12.03.2024-17.03.2024", value = "70000 тг")
        SalaryItem2(label = "12.03.2024-17.03.2024", value = "30000 тг")
        SalaryItem2(label = "12.03.2024-17.03.2024", value = "20000 тг")
        Spacer(Modifier.weight(1f))
    }
}


@Composable
fun SalaryItem2(label: String, value: String) {
    Card(
        backgroundColor = Color(0xFF364156),
        shape = RoundedCornerShape(18.dp),
        elevation = 52.dp,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(270.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}