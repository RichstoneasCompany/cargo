package com.example.richstonecargo.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.domain.model.TruckInfo
import com.example.richstonecargo.global.UserInfoManager
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoBottomBar
import com.example.richstonecargo.presentation.layout.CargoTopBar
import com.example.richstonecargo.presentation.util.DateComponent
import com.example.richstonecargo.presentation.util.base64ToBitmap
import com.example.richstonecargo.presentation.util.extractDateComponent

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val currentRoute = Screen.TripListScreen.route

    val state = viewModel.state.value

    val profilePicture = remember {
        UserInfoManager.getUserInfo()?.profilePicture?.let {
            base64ToBitmap(it)
        }
    }
    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = {
            CargoTopBar(
                navController = navController
            )
        },
        bottomBar = {
            CargoBottomBar(
                selectedRoute = Screen.ProfileScreen.route,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF0A0E21)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ProfileTitle()
            ProfileHeaderWithInformation(
                name = state.profileDetails?.firstname ?: "",
                surname = state.profileDetails?.lastname ?: "",
                birthDate = "${
                    state.profileDetails?.birthDate?.let {
                        extractDateComponent(
                            it,
                            DateComponent.DAY
                        )
                    }
                } ${
                    state.profileDetails?.birthDate?.let {
                        extractDateComponent(
                            it, DateComponent.MONTH_NAME
                        ).replaceFirstChar(Char::titlecase)
                    }
                } ${
                    state.profileDetails?.birthDate?.let {
                        extractDateComponent(
                            it, DateComponent.YEAR
                        )
                    }
                }",
                gender = state.profileDetails?.gender?.displayName ?: "",
                profilePicture = profilePicture
            )
            TextBetween()
            TransportCard(state.profileDetails?.truckInfo)
        }
    }
}

@Composable
fun ProfileTitle() {
    Text(
        text = "Профиль",
        color = Color.White,
        fontSize = 32.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun ProfileHeaderWithInformation(name: String, surname: String, birthDate: String, gender: String, profilePicture: android.graphics.Bitmap?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 90.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(210.dp)
                .padding(end = 1.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0f)
        ) {
            if (profilePicture != null) {
                Image(
                    bitmap = profilePicture.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.kz),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(Modifier.width(60.dp))
        ProfileInformationSection(name, surname, birthDate, gender)
    }
}

@Composable
fun ProfileInformationSection(name: String, surname: String, birthDate: String, gender: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ProfileInformation(label = "Имя", value = name)
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Дата рождения",
                style = MaterialTheme.typography.caption,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(2.dp)
            )
            BirthDateRow(birthDate)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            ProfileInformation(label = "Фамилия", value = surname)
            Spacer(modifier = Modifier.height(18.dp))
            ProfileInformation(label = "Пол", value = gender)
        }
    }
}

@Composable
fun BirthDateRow(birthDate: String) {
    val birthDateParts = birthDate.split(" ")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        birthDateParts.forEachIndexed { index, datePart ->
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .padding(horizontal = 4.dp),
                color = Color(0xFF364156),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = datePart,
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (index < birthDateParts.lastIndex) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun ProfileInformation(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(2.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(56.dp)
                .padding(vertical = 4.dp),
            color = Color(0xFF364156),
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TextBetween() {
    Text(
        text = "Транспорт",
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp, bottom = 14.dp, start = 100.dp)
            .wrapContentSize(Alignment.TopStart)
    )
}

@Composable
fun TransportCard(truckInfo: TruckInfo?) {
    Card(
        backgroundColor = Color(0xFF364156),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 110.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (truckInfo == null) {
                Text(text = "Грузовик не присвоен")
            } else {
                TransportInformation("Марка:", truckInfo.model)
                TransportInformation("Номер:", truckInfo.numberPlate)
                TransportInformation("Грузоподъемность тон:", truckInfo.maxPermMass)
            }
        }
    }
}

@Composable
fun TransportInformation(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(0.8f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier
                .weight(0.8f)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}
