package com.example.richstonecargo.presentation.calculation_list

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.domain.model.PayslipDetails
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import com.example.richstonecargo.presentation.trip_salary.TripSalaryScreenViewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripSalaryScreen(
    navController: NavController,
    yearMonth: String?,
    viewModel: TripSalaryScreenViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val payslipDetails by viewModel.payslipDetails.collectAsState()

    LaunchedEffect(yearMonth) {
        yearMonth?.let {
            viewModel.fetchPayslipDetails(it)
        }
    }

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
            if (payslipDetails.isEmpty()) {
                CircularProgressIndicator(color = Color.White)
            } else {
                LargeCard2(navController = navController, payslipDetails = payslipDetails)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LargeCard2(navController: NavController, payslipDetails: List<PayslipDetails>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 180.dp)
            .heightIn(min = 250.dp, max = 450.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF364156)
    ) {
        ContentCard2(navController = navController, payslipDetails = payslipDetails)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentCard2(navController: NavController, payslipDetails: List<PayslipDetails>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
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
            }
            // Title Text
            Text(
                text = "Оклад по рейсам",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.weight(1f))
        payslipDetails.forEach { detail ->
            SalaryItem2(
                label = "${detail.departureTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))} - ${
                    detail.arrivalTime.format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    )
                }", value = "${detail.baseSalary} тг"
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun SalaryItem2(label: String, value: String) {
    Card(
        backgroundColor = Color(0xFF364156),
        shape = RoundedCornerShape(18.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 80.dp, end = 80.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(3f)
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}
