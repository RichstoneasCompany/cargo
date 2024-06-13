package com.example.richstonecargo.presentation.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.global.UserInfoManager
import com.example.richstonecargo.presentation.util.base64ToBitmap


@Composable
fun CargoTopBar(
    navController: NavController,
    isLoggedIn: Boolean = true,
    notificationCount: Int = 0
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val currentRoute = remember { mutableStateOf(navController.currentDestination?.route ?: "") }
    val MontserratFontFamily = FontFamily(
        Font(R.font.montserrat_black)
    )

    val profilePicture = remember {
        UserInfoManager.getUserInfo()?.profilePicture?.let {
            base64ToBitmap(
                it
            )
        }
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: ""
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

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
            if (isLoggedIn) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                ) {
                    IconButton(onClick = { navController.navigate("notification_screen") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = "Уведомления",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    if (notificationCount > 0) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .offset(x = 4.dp, y = 6.dp)
                                .size(20.dp)
                                .background(Color(0xFF0066FF), shape = CircleShape)
                                .align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = notificationCount.toString(),
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = UserInfoManager.getUserInfo()?.name ?: "Test",
                        color = Color.White.copy(alpha = 1f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = UserInfoManager.getUserInfo()?.surname ?: "User",
                        color = Color.White.copy(alpha = 1f),
                        fontSize = 14.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier
                            .size(120.dp)
                    ) {
                        if (profilePicture != null) {
                            Image(
                                bitmap = profilePicture.asImageBitmap(),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.kz),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color(0xFF364156))
                            .wrapContentSize(Alignment.TopStart)
                            .fillMaxWidth(0.2f)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = "Профиль"
                                expanded = false
                                navController.navigate("profile_screen")
                            },
                            modifier = Modifier
                                .background(
                                    if (currentRoute.value == "profile_screen") Color(
                                        0xFF0066FF
                                    ) else Color.Transparent
                                )
                        ) {
                            Text(
                                "Профиль",
                                color = Color.White,
                                fontFamily = FontFamily(
                                    Font(R.font.montserrat_regular)
                                )
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = "Выйти"
                                expanded = false
                                {}
                                navController.navigate("login_screen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier
                                .background(
                                    if (selectedOption == "Выйти") Color(
                                        0xFF0066FF
                                    ) else Color.Transparent
                                )
                        ) {
                            Text(
                                "Выйти",
                                color = Color.White,
                                fontFamily = FontFamily(
                                    Font(R.font.montserrat_regular)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
