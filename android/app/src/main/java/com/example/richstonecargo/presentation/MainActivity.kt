package com.example.richstonecargo.presentation

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.richstonecargo.presentation.about_trip.AboutTripScreen
import com.example.richstonecargo.presentation.calculation_list.CalculationListScreen
import com.example.richstonecargo.presentation.calculation_list.TripSalaryScreen
import com.example.richstonecargo.presentation.forgot_password.ConfirmPasswordScreen
import com.example.richstonecargo.presentation.forgot_password.ForgotPasswordScreen
import com.example.richstonecargo.presentation.forgot_password.SmsScreenForgot
import com.example.richstonecargo.presentation.help.HelpDetailScreen
import com.example.richstonecargo.presentation.help.HelpScreen
import com.example.richstonecargo.presentation.login.LoginScreen
import com.example.richstonecargo.presentation.notification.NotificationScreen
import com.example.richstonecargo.presentation.notification.TripChangesScreen
import com.example.richstonecargo.presentation.notification.UpdateApplicationScreen
import com.example.richstonecargo.presentation.profile.ProfileScreen
import com.example.richstonecargo.presentation.registration.PasswordScreen
import com.example.richstonecargo.presentation.registration.RegistrationScreen
import com.example.richstonecargo.presentation.registration.SmsScreen
import com.example.richstonecargo.presentation.trip_info.StartTripScreen
import com.example.richstonecargo.presentation.trip_info.TripInfoScreen
import com.example.richstonecargo.presentation.trip_list.TripListScreen
import com.example.richstonecargo.presentation.ui.theme.CargoAppTheme
import dagger.hilt.android.AndroidEntryPoint

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
                    val currentRoute = navController.currentDestination?.route

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
                            route = Screen.TripInfoScreen.route
                        ) {
                            TripInfoScreen(navController)
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
                            route = Screen.StartTripScreen.route
                        ) {
                            StartTripScreen(navController)
                        }
                        composable(
                            route = Screen.AboutTripScreen.route
                        ) {
                            AboutTripScreen(navController)
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
                            route = Screen.SmsScreenForgot.route
                        ) {
                            SmsScreenForgot(navController)
                        }
                        composable(
                            route = Screen.ConfirmPasswordScreen.route
                        ) {
                            ConfirmPasswordScreen(navController)
                        }
                        composable(
                            route = Screen.NotificationScreen.route
                        ) {
                            NotificationScreen(navController)
                        }
                        composable(
                            route = Screen.TripChangesScreen.route
                        ) {
                            TripChangesScreen(navController)
                        }
                        composable(
                            route = Screen.TripSalaryScreen.route
                        ) {
                            TripSalaryScreen(navController)
                        }
                        composable(
                            route = Screen.HelpDetailScreen.route
                        ) {
                            HelpDetailScreen(navController)
                        }
                        composable(
                            route = Screen.UpdateApplicationScreen.route
                        ) {
                            UpdateApplicationScreen(navController)
                        }
                    }
                }
            }
        }
    }
}