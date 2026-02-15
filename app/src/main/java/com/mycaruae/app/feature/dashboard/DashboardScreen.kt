package com.mycaruae.app.feature.dashboard

import androidx.compose.runtime.Composable
import com.mycaruae.app.ui.components.PlaceholderScreen

@Composable
fun DashboardScreen(
    onNavigateToMileageEntry: () -> Unit,
    onNavigateToMaintenanceAdd: () -> Unit,
    onNavigateToReminderCreate: () -> Unit,
) {
    PlaceholderScreen(title = "Dashboard")
    // TODO: Vehicle card, reg/inspection status, health score, quick actions
}
