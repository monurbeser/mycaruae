package com.mycaruae.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyCarUaeApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            // Registration expiry channel
            val regChannel = NotificationChannel(
                CHANNEL_REGISTRATION,
                "Registration Reminders",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Reminders for vehicle registration expiry"
            }

            // Inspection expiry channel
            val inspChannel = NotificationChannel(
                CHANNEL_INSPECTION,
                "Inspection Reminders",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Reminders for vehicle inspection expiry"
            }

            // Maintenance channel
            val maintChannel = NotificationChannel(
                CHANNEL_MAINTENANCE,
                "Maintenance Reminders",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Reminders for vehicle maintenance"
            }

            manager.createNotificationChannels(listOf(regChannel, inspChannel, maintChannel))
        }
    }

    companion object {
        const val CHANNEL_REGISTRATION = "registration_reminders"
        const val CHANNEL_INSPECTION = "inspection_reminders"
        const val CHANNEL_MAINTENANCE = "maintenance_reminders"
    }
}