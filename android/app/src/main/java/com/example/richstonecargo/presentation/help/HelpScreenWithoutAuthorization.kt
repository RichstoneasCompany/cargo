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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.richstonecargo.R
import com.example.richstonecargo.domain.model.Topic
import com.example.richstonecargo.domain.model.TopicListState
import com.example.richstonecargo.presentation.Screen
import com.example.richstonecargo.presentation.layout.CargoTopBarWithoutProfile

@Composable
fun HelpScreenWithoutAuthorization(
    navController: NavController,
    topicListViewModel: TopicListViewModel = hiltViewModel()
) {
    val primaryColor = Color(0xFF0A0E21)
    val cardBackgroundColor = Color(0xFF364156)
    val textColor = Color.White
    val state by topicListViewModel.state.observeAsState(TopicListState())

    Scaffold(
        modifier = Modifier.background(primaryColor),
        topBar = { CargoTopBarWithoutProfile() },
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
                state.topics?.let { topics: List<Topic> ->
                    items(topics) { topic: Topic ->
                        HelpMenuItemWithoutAuthorization(
                            topic,
                            cardBackgroundColor,
                            textColor,
                            navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HelpMenuItemWithoutAuthorization(
    topic: Topic,
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
                navController.navigate("${Screen.HelpDetailScreenWithoutAuthorization.route}/${topic.id}")
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
                    text = topic.name,
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
