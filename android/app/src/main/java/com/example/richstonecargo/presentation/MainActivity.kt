package com.example.richstonecargo.presentation

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.richstonecargo.presentation.about_trip.AboutTripScreen
import com.example.richstonecargo.presentation.calculation_list.CalculationListScreen
import com.example.richstonecargo.presentation.calculation_list.TripSalaryScreen
import com.example.richstonecargo.presentation.dispatcher.DispatcherScreen
import com.example.richstonecargo.presentation.forgot_password.ConfirmPasswordScreen
import com.example.richstonecargo.presentation.forgot_password.ForgotPasswordScreen
import com.example.richstonecargo.presentation.forgot_password.SmsScreenForgot
import com.example.richstonecargo.presentation.help.HelpDetailScreen
import com.example.richstonecargo.presentation.help.HelpDetailScreenWithoutAuthorization
import com.example.richstonecargo.presentation.help.HelpScreen
import com.example.richstonecargo.presentation.help.HelpScreenWithoutAuthorization
import com.example.richstonecargo.presentation.login.LoginScreen
import com.example.richstonecargo.presentation.notification.NotificationScreen
import com.example.richstonecargo.presentation.notification.TripChangesScreen
import com.example.richstonecargo.presentation.notification.UpdateApplicationScreen
import com.example.richstonecargo.presentation.profile.ProfileScreen
import com.example.richstonecargo.presentation.realtime_notification.RealtimeNotificationViewModel
import com.example.richstonecargo.presentation.registration.PasswordScreen
import com.example.richstonecargo.presentation.registration.RegistrationScreen
import com.example.richstonecargo.presentation.registration.SmsScreen
import com.example.richstonecargo.presentation.trip_info.ActiveTripScreen
import com.example.richstonecargo.presentation.trip_list.TripListScreen
import com.example.richstonecargo.presentation.ui.theme.CargoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContent {
            CargoAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    val notificationViewModel: RealtimeNotificationViewModel by viewModels()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

                    // Observe the message LiveData
                    val message by notificationViewModel.message.observeAsState()

                    // Show Snackbar when a new message is received
                    LaunchedEffect(message) {
                        message?.let {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    }

                    // UI content
                    SnackbarHost(hostState = snackbarHostState) {
                        Snackbar(snackbarData = it)
                    }


                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ) {
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(navController)
                        }
                        composable(
                            route = Screen.RegistrationScreen.route
                        ) {
                            RegistrationScreen(navController)
                        }
                        composable(
                            route = Screen.TripListScreen.route
                        ) {
                            TripListScreen(navController)
                        }
                        composable(
                            route = "${Screen.SmsScreen.route}/{mobileNumber}",
                            arguments = listOf(navArgument("mobileNumber") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            SmsScreen(
                                navController = navController,
                                mobileNumber = backStackEntry.arguments?.getString("mobileNumber")
                                    ?: "+7",
                            )
                        }
                        composable(
                            route = Screen.ForgotPasswordScreen.route
                        ) {
                            ForgotPasswordScreen(navController)
                        }
                        composable(
                            route = Screen.CalculationListScreen.route
                        ) {
                            CalculationListScreen(navController)
                        }
                        composable(
                            route = Screen.HelpScreen.route
                        ) {
                            HelpScreen(navController)
                        }
                        composable(
                            route = Screen.HelpScreenWithoutAuthorization.route
                        ) {
                            HelpScreenWithoutAuthorization(navController)
                        }
                        composable(
                            route = Screen.ActiveTripScreen.route
                        ) {
                            ActiveTripScreen(navController)
                        }
//                        composable(
//                            route = Screen.StartTripScreen.route
//                        ) {
//                            StartTripScreen(navController)
//                        }
                        composable(
                            route = "${Screen.AboutTripScreen.route}/{tripId}",
                            arguments = listOf(navArgument("tripId") {
                                type = NavType.LongType
                            })
                        ) { backStackEntry ->
                            AboutTripScreen(
                                navController = navController,
                                tripId = backStackEntry.arguments?.getLong("tripId")
                                    ?: 1,
                            )
                        }
                        composable(
                            route = Screen.ProfileScreen.route
                        ) {
                            ProfileScreen(navController)
                        }
                        composable(
                            route = "${Screen.PasswordScreen.route}/{mobileNumber}",
                            arguments = listOf(navArgument("mobileNumber") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            PasswordScreen(
                                navController = navController,
                                mobileNumber = backStackEntry.arguments?.getString("mobileNumber")
                                    ?: "+7",
                            )
                        }
                        composable(
                            route = "${Screen.SmsScreenForgot.route}/{mobileNumber}",
                            arguments = listOf(navArgument("mobileNumber") {
                                type = NavType.StringType
                            })
                        ) {
                            SmsScreenForgot(
                                navController,
                                mobileNumber = it.arguments?.getString("mobileNumber")
                                    ?: "+7"
                            )
                        }
                        composable(
                            route = "${Screen.ConfirmPasswordScreen.route}/{mobileNumber}",
                            arguments = listOf(navArgument("mobileNumber") {
                                type = NavType.StringType
                            })
                        ) {
                            ConfirmPasswordScreen(
                                navController,
                                mobileNumber = it.arguments?.getString("mobileNumber")
                                    ?: "+7"
                            )
                        }
                        composable(
                            route = Screen.NotificationScreen.route
                        ) {
                            NotificationScreen(navController)
                        }
                        composable(
                            route = Screen.TripChangesScreen.route,
                        ) { backStackEntry ->
                            TripChangesScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            route = "${Screen.TripSalaryScreen.route}/{yearMonth}",
                            arguments = listOf(navArgument("yearMonth") {
                                type = NavType.StringType
                            })
                        ) {
                            TripSalaryScreen(
                                navController,
                                yearMonth = it.arguments?.getString("yearMonth")
                            )
                        }
                        composable(
                            route = "${Screen.HelpDetailScreen.route}/{topicId}",
                            arguments = listOf(navArgument("topicId") {
                                type = NavType.LongType
                            })
                        ) { backStackEntry ->
                            val topicId = backStackEntry.arguments?.getLong("topicId") ?: 0L
                            HelpDetailScreen(navController, topicId)
                        }
                        composable(
                            route = "${Screen.HelpDetailScreenWithoutAuthorization.route}/{topicId}",
                            arguments = listOf(navArgument("topicId") {
                                type = NavType.LongType
                            })
                        ) { backStackEntry ->
                            val topicId = backStackEntry.arguments?.getLong("topicId") ?: 0L
                            HelpDetailScreenWithoutAuthorization(navController, topicId)
                        }
                        composable(
                            route = Screen.UpdateApplicationScreen.route
                        ) {
                            UpdateApplicationScreen(navController)
                        }
                        composable(
                            route = Screen.DispatcherScreen.route
                        ) {
                            DispatcherScreen(navController)
                        }
                    }
                }
            }
        }
    }
}