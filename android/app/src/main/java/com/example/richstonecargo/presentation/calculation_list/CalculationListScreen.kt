package com.example.richstonecargo.presentation.calculation_list

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
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import kotlin.math.roundToInt


@Composable
fun CalculationListScreen(
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
            LargeCard(navController = navController)
        }
    }
}

@Composable
fun LargeCard(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 150.dp)
            .heightIn(min = 350.dp, max = 500.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF364156)
    ) {
        ContentCard(navController = navController)
    }
}

@Composable
fun ContentCard(navController: NavController) {
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
            PeriodDropdown()
        }

        Spacer(Modifier.weight(1f))

        SalaryItem(label = "Оклад по рейсам",
            value = "200000 тг",
            showEditButton = true,
            onEditClick = { navController.navigate("trip_salary_screen") }
        )
        SalaryItem(label = "Премия", value = "70000 тг")
        SalaryItem(label = "НДФЛ", value = "30000 тг")
        SalaryItem(label = "Штраф", value = "20000 тг")

        Spacer(Modifier.weight(2f))

        TotalToBePaidItem(total = "220000 тг")
    }
}

@Composable
fun PeriodDropdown() {
    var expanded by remember { mutableStateOf(false) }
    val items =
        listOf("Январь 2024", "Февраль 2024", "Март 2024", "Апрель 2024", "Май 2024", "Июнь 2024")
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
                text = items[selectedIndex],
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
            }
        )
    }
}

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
                    val item = items[index]
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





