package com.mycaruae.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.runtime.collectAsState
import com.mycaruae.app.notification.ReminderWorker
import com.mycaruae.app.ui.MyCarUaeAppRoot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: com.mycaruae.app.data.datastore.UserPreferences

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* Granted or denied, worker will check before showing */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        ReminderWorker.schedule(this)
        
        // Collect theme preference synchronously or use a specialized approach?
        // Better: Pass preferences/flow to MyCarUaeAppRoot or collect here.
        // For simplicity and to avoid blocking main thread too much, we'll let Compose collect it.
        // But MainActivity sets Content.
        // Let's pass the repo/prefs to Root, or better yet, just let Root collect it if it has access.
        // However, Root is a Composable.
        
        setContent {
            val userData = userPreferences.userData.collectAsState(initial = null).value
            // Wait for data or use default system
            val theme = userData?.theme ?: "system"
            
            MyCarUaeAppRoot(theme = theme)
        }
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