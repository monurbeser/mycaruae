package com.mycaruae.app.notification

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mycaruae.app.MainActivity
import com.mycaruae.app.MyCarUaeApp
import com.mycaruae.app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun showRegistrationReminder(
        title: String,
        body: String,
        notificationId: Int,
        vehicleLabel: String = "",
        reminderType: String = "REGISTRATION",
    ) {
        show(
            channelId = MyCarUaeApp.CHANNEL_REGISTRATION,
            title = title,
            body = body,
            notificationId = notificationId,
            vehicleLabel = vehicleLabel,
            reminderType = reminderType,
        )
    }

    fun showInspectionReminder(
        title: String,
        body: String,
        notificationId: Int,
        vehicleLabel: String = "",
        reminderType: String = "INSPECTION",
    ) {
        show(
            channelId = MyCarUaeApp.CHANNEL_INSPECTION,
            title = title,
            body = body,
            notificationId = notificationId,
            vehicleLabel = vehicleLabel,
            reminderType = reminderType,
        )
    }

    fun showMaintenanceReminder(
        title: String,
        body: String,
        notificationId: Int,
        vehicleLabel: String = "",
        reminderType: String = "MAINTENANCE",
    ) {
        show(
            channelId = MyCarUaeApp.CHANNEL_MAINTENANCE,
            title = title,
            body = body,
            notificationId = notificationId,
            vehicleLabel = vehicleLabel,
            reminderType = reminderType,
        )
    }

    private fun show(
        channelId: String,
        title: String,
        body: String,
        notificationId: Int,
        vehicleLabel: String,
        reminderType: String,
    ) {
        if (!hasPermission()) return

        val intent = Intent(context, MainActivity::class.java).apply {
            // Don't restart the app — just bring existing task to front
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("reminder_title", title)
            putExtra("reminder_body", body)
            putExtra("reminder_vehicle", vehicleLabel)
            putExtra("reminder_type", reminderType)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val priority = when (channelId) {
            MyCarUaeApp.CHANNEL_REGISTRATION,
            MyCarUaeApp.CHANNEL_INSPECTION -> NotificationCompat.PRIORITY_HIGH
            else -> NotificationCompat.PRIORITY_DEFAULT
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}