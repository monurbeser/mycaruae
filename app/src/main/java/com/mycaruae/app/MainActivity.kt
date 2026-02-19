package com.mycaruae.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.mycaruae.app.notification.ReminderWorker
import com.mycaruae.app.ui.MyCarUaeAppRoot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ReminderPopupData(
    val title: String,
    val body: String,
    val vehicleLabel: String,
    val reminderType: String,
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _reminderPopup = MutableStateFlow<ReminderPopupData?>(null)
    val reminderPopup: StateFlow<ReminderPopupData?> = _reminderPopup.asStateFlow()

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* Granted or denied, worker will check before showing */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        ReminderWorker.schedule(this)

        handleReminderIntent(intent)

        setContent {
            MyCarUaeAppRoot(
                theme = "system",
                reminderPopup = reminderPopup,
                onDismissReminder = { _reminderPopup.value = null },
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleReminderIntent(intent)
    }

    private fun handleReminderIntent(intent: Intent?) {
        val title = intent?.getStringExtra("reminder_title") ?: return
        val body = intent.getStringExtra("reminder_body") ?: ""
        val vehicle = intent.getStringExtra("reminder_vehicle") ?: ""
        val type = intent.getStringExtra("reminder_type") ?: ""

        _reminderPopup.value = ReminderPopupData(
            title = title,
            body = body,
            vehicleLabel = vehicle,
            reminderType = type,
        )

        // Clear extras so it doesn't re-trigger
        intent.removeExtra("reminder_title")
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}