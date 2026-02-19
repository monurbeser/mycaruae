package com.mycaruae.app.feature.reminders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mycaruae.app.data.database.entity.ReminderEntity
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.components.EmptyScreen
import com.mycaruae.app.ui.components.LoadingScreen
import com.mycaruae.app.ui.components.StatusCard
import com.mycaruae.app.ui.theme.CocIcons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RemindersScreen(
    onNavigateToCreate: () -> Unit,
    viewModel: RemindersViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CocTopBar(title = "Reminders")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreate,
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = CocIcons.Add,
                    contentDescription = "Create reminder",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingScreen(modifier = Modifier.padding(padding))
            }
            !state.hasVehicle -> {
                EmptyScreen(
                    title = "No vehicle",
                    description = "Add a vehicle first to see reminders.",
                    modifier = Modifier.padding(padding),
                )
            }
            else -> {
                val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp),
                ) {
                    // Vehicle Expiries - grouped by vehicle
                    val grouped = state.expiries.groupBy { it.vehicleLabel }
                    grouped.forEach { (vehicleLabel, expiries) ->
                        item(key = "header_$vehicleLabel") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = vehicleLabel,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        items(
                            items = expiries,
                            key = { "${it.type}_${it.vehicleLabel}" },
                        ) { expiry ->
                            StatusCard(
                                title = expiry.title,
                                daysRemaining = expiry.daysRemaining,
                                expiryDateFormatted = dateFormatter.format(Date(expiry.expiryDate)),
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    // Manual Reminders section
                    if (state.manualReminders.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "My Reminders",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(state.manualReminders, key = { it.id }) { reminder ->
                            ManualReminderItem(
                                reminder = reminder,
                                onComplete = { viewModel.completeReminder(reminder) },
                                onDismiss = { viewModel.dismissReminder(reminder) },
                            )
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                            )
                        }
                    }

                    // Completed section
                    if (state.completedReminders.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Completed",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(state.completedReminders, key = { it.id }) { reminder ->
                            CompletedReminderItem(reminder = reminder)
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
private fun ManualReminderItem(
    reminder: ReminderEntity,
    onComplete: () -> Unit,
    onDismiss: () -> Unit,
) {
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = CocIcons.Reminders,
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = reminder.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (reminder.dueDate != null) {
                Text(
                    text = dateFormatter.format(Date(reminder.dueDate)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (!reminder.note.isNullOrBlank()) {
                Text(
                    text = reminder.note,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                )
            }
        }
        IconButton(onClick = onComplete) {
            Icon(
                imageVector = CocIcons.Check,
                contentDescription = "Complete",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        IconButton(onClick = onDismiss) {
            Icon(
                imageVector = CocIcons.Close,
                contentDescription = "Dismiss",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CompletedReminderItem(reminder: ReminderEntity) {
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = CocIcons.Check,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = reminder.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                textDecoration = TextDecoration.LineThrough,
            )
            if (reminder.completedAt != null) {
                Text(
                    text = "Done ${dateFormatter.format(Date(reminder.completedAt))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                )
            }
        }
    }
}