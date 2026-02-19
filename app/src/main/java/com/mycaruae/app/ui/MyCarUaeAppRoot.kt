package com.mycaruae.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mycaruae.app.ReminderPopupData
import com.mycaruae.app.navigation.BottomNavBar
import com.mycaruae.app.navigation.CocNavHost
import com.mycaruae.app.navigation.bottomNavScreens
import com.mycaruae.app.ui.theme.CocIcons
import com.mycaruae.app.ui.theme.MyCarUaeTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MyCarUaeAppRoot(
    theme: String,
    reminderPopup: StateFlow<ReminderPopupData?>? = null,
    onDismissReminder: () -> Unit = {},
) {
    MyCarUaeTheme(themePreference = theme) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val showBottomBar = currentRoute in bottomNavScreens.map { it.route }

        // Reminder popup from notification
        val popupData = reminderPopup?.collectAsStateWithLifecycle()?.value
        if (popupData != null) {
            ReminderPopupDialog(
                data = popupData,
                onDismiss = onDismissReminder,
            )
        }

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomNavBar(
                        navController = navController,
                        currentRoute = currentRoute,
                    )
                }
            },
        ) { innerPadding ->
            CocNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun ReminderPopupDialog(
    data: ReminderPopupData,
    onDismiss: () -> Unit,
) {
    val typeLabel = when (data.reminderType.uppercase()) {
        "REGISTRATION" -> "Registration"
        "INSPECTION" -> "Inspection"
        "MAINTENANCE" -> "Maintenance"
        else -> data.reminderType
    }

    val typeIcon = when (data.reminderType.uppercase()) {
        "REGISTRATION" -> CocIcons.Calendar
        "INSPECTION" -> CocIcons.Check
        "MAINTENANCE" -> CocIcons.Maintenance
        else -> CocIcons.Reminders
    }

    val typeColor = when (data.reminderType.uppercase()) {
        "REGISTRATION" -> MaterialTheme.colorScheme.error
        "INSPECTION" -> MaterialTheme.colorScheme.tertiary
        "MAINTENANCE" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.primary
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = typeIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = typeColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Vehicle info
                if (data.vehicleLabel.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CocIcons.Car,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = data.vehicleLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Reminder type badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = CocIcons.Reminders,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = typeColor,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = typeLabel,
                        style = MaterialTheme.typography.labelLarge,
                        color = typeColor,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Body
                Text(
                    text = data.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
    )
}