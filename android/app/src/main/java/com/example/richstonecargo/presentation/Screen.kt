package com.example.richstonecargo.presentation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegistrationScreen : Screen("registration_screen")
    object TripListScreen : Screen("trip_list_screen")
    object TripInfoScreen : Screen("trip_info_screen")
    object SmsScreen : Screen("sms_screen")
    object ForgotPasswordScreen : Screen("forgot_password_screen")
    object CalculationListScreen : Screen("calculation_list_screen")
    object HelpScreen : Screen("help_screen")
    object StartTripScreen : Screen("start_trip_screen")
    object AboutTripScreen : Screen("about_trip_screen")
    object ProfileScreen : Screen("profile_screen")
    object PasswordScreen : Screen("password_screen")
    object SmsScreenForgot : Screen("sms_screen_forgot")
    object ConfirmPasswordScreen : Screen("confirm_password_screen")
    object NotificationScreen : Screen("notification_screen")
    object TripChangesScreen : Screen("trip_changes_screen")
    object TripSalaryScreen : Screen("trip_salary_screen")
    object HelpDetailScreen : Screen("help_detail_screen")
    object UpdateApplicationScreen : Screen("update_application_screen")
}
