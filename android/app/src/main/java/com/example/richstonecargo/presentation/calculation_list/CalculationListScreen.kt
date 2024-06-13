package com.example.richstonecargo.presentation.calculation_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.domain.model.SalaryInfo
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt


@RequiresApi(Build.VERSION_CODES.O)
fun formatMonthYear(yearMonth: String): String {
    val date = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
    val formattedMonthYear = date.format(DateTimeFormatter.ofPattern("LLLL yyyy", Locale("ru")))
    return formattedMonthYear.replaceFirstChar { it.uppercase() }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalculationListScreen(
    navController: NavController,
    viewModel: CalculationListViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val salaryInfo by viewModel.salaryInfo.collectAsState()
    val availableMonths by viewModel.availableMonths.collectAsState()
    var selectedYearMonth by remember { mutableStateOf(availableMonths[0]) }

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
            if (availableMonths.isEmpty()) {
                CircularProgressIndicator(color = Color.White)
            } else {
                LargeCard(
                    navController = navController,
                    salaryInfo = salaryInfo,
                    availableMonths = availableMonths,
                    onPeriodSelected = { month ->
                        viewModel.fetchSalaryInfo(month)
                        selectedYearMonth = month
                    },
                    selectedYearMonth = selectedYearMonth
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LargeCard(
    navController: NavController,
    salaryInfo: SalaryInfo?,
    availableMonths: List<String>,
    onPeriodSelected: (String) -> Unit,
    selectedYearMonth: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 150.dp)
            .heightIn(min = 350.dp, max = 500.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF364156)
    ) {
        ContentCard(
            navController = navController,
            salaryInfo = salaryInfo,
            availableMonths = availableMonths,
            onPeriodSelected = onPeriodSelected,
            selectedYearMonth = selectedYearMonth
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentCard(
    navController: NavController,
    salaryInfo: SalaryInfo?,
    availableMonths: List<String>,
    onPeriodSelected: (String) -> Unit,
    selectedYearMonth: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(90.dp))
            Text(
                text = "Период:",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Spacer(modifier = Modifier.width(250.dp))
            PeriodDropdown(items = availableMonths, onPeriodSelected = onPeriodSelected)
        }

        Spacer(Modifier.weight(1f))

        SalaryItem(label = "Оклад по рейсам",
            value = salaryInfo?.baseSalary?.toString() ?: "Загрузка...",
            showEditButton = true,
            onEditClick = { navController.navigate("${Screen.TripSalaryScreen.route}/${selectedYearMonth}") })
        SalaryItem(label = "Премия", value = salaryInfo?.bonuses?.toString() ?: "Загрузка...")
        SalaryItem(label = "НДФЛ", value = salaryInfo?.incomeTax?.toString() ?: "Загрузка...")
        SalaryItem(label = "Штраф", value = salaryInfo?.fine?.toString() ?: "Загрузка...")

        Spacer(Modifier.weight(2f))

        TotalToBePaidItem(total = salaryInfo?.totalPay?.toString() ?: "Загрузка...")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PeriodDropdown(items: List<String>, onPeriodSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val buttonSize = remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = Modifier
            .clickable { expanded = true }
            .border(width = 4.dp, color = Color(0xFF0066FF), shape = RoundedCornerShape(12.dp))
            .background(Color(0xFF3D404E), shape = RoundedCornerShape(12.dp))
            .padding(vertical = 14.dp, horizontal = 24.dp)
            .fillMaxWidth(0.6f)
            .onGloballyPositioned { layoutCoordinates ->
                buttonSize.value = layoutCoordinates.size.toSize()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.weight(0.2f))
            Text(
                text = if (items.isNotEmpty()) formatMonthYear(items[selectedIndex]) else "Выберите",
                color = Color(0xFF0066FF),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
            )
            Spacer(Modifier.weight(0.1f))
            Icon(
                painter = painterResource(id = R.drawable.dropdownicon),
                contentDescription = "Dropdown",
                tint = Color(0xFF0066FF),
                modifier = Modifier.size(24.dp)
            )
        }

        DropdownPopup(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            buttonSize = buttonSize.value,
            items = items,
            selectedIndex = selectedIndex,
            onItemSelected = { index ->
                selectedIndex = index
                expanded = false
                onPeriodSelected(items[index])
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DropdownPopup(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    buttonSize: Size,
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val density = LocalDensity.current

    if (expanded) {
        Popup(
            alignment = Alignment.TopCenter,
            onDismissRequest = { onItemSelected(selectedIndex) },
            offset = IntOffset(0, with(density) { buttonSize.height.roundToInt() })
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val dropdownWidth = screenWidth * 0.225f
            LazyColumn(
                modifier = Modifier
                    .width(dropdownWidth)
                    .heightIn(max = 230.dp),
            ) {
                items(items.size) { index ->
                    val backgroundColor =
                        if (selectedIndex == index) Color(0xFF0066FF) else Color(0xFF364156)
                    val item = formatMonthYear(items[index])
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(index)
                        },
                        modifier = Modifier.background(backgroundColor)
                    ) {
                        Text(
                            text = item,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    if (index < items.size - 1) {
                        Divider(color = Color.White, thickness = 0.7.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun SalaryItem(
    label: String,
    value: String,
    showEditButton: Boolean = false,
    navController: NavController? = null,
    onEditClick: () -> Unit = {}
) {
    Card(
        backgroundColor = Color(0xFF364156),
        shape = RoundedCornerShape(18.dp),
        elevation = 52.dp,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.wrapContentWidth(Alignment.End)
            )

            if (showEditButton) {
                IconButton(onClick = { onEditClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notificationpageicon),
                        contentDescription = "SalaryPage",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            } else {
                Spacer(Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun TotalToBePaidItem(total: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1.5f))
        Text(
            text = "К выплате:",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(3f)
        )
        Spacer(Modifier.weight(2.5f))
        Text(
            text = total,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.weight(3f)
        )
        Spacer(Modifier.weight(1f))
    }
}
