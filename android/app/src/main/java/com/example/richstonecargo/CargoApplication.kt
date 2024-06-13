package com.example.richstonecargo

import android.app.Application
import android.util.Log
import com.example.richstonecargo.global.UserInfoManager
import com.example.richstonecargo.presentation.realtime_notification.RealtimeNotificationViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CargoApplication : Application() {

    lateinit var realtimeNotificationViewModel: RealtimeNotificationViewModel

    override fun onCreate() {
        super.onCreate()
        setupUncaughtExceptionHandler()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed: ${task.exception}")
                return@addOnCompleteListener
            }
            val token = task.result
            UserInfoManager.setDeviceToken(token)
            Log.d("Device Token", "$token")
        }
    }

    private fun setupUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread: Thread, e: Throwable? ->
            // Log the stack trace manually
            Log.e(
                "UncaughtException",
                "Unhandled exception in thread: " + thread.name,
                e
            )

            // Optionally, write the stack trace to a file or send it to a server

            // Use the default Android handler to process the crash further
            System.exit(1) // Ensure the app exits
        }
    }
}