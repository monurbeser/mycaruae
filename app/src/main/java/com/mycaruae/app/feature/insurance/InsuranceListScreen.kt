package com.mycaruae.app.feature.insurance

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mycaruae.app.ui.components.EmptyScreen
import com.mycaruae.app.ui.components.LoadingScreen
import com.mycaruae.app.ui.theme.CocIcons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Standalone screen — kept for backward compat.
 */
@Composable
fun InsuranceListScreen(
    onNavigateToAdd: () -> Unit,
    viewModel: InsuranceListViewModel = hiltViewModel(),
) {
    InsuranceListContent(viewModel = viewModel)
}

/**
 * Content-only composable — no Scaffold, no FAB.
 * Used inside ServicesScreen tabs.
 */
@Composable
fun InsuranceListContent(
    viewModel: InsuranceListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    if (state.pendingDeleteId != null) {
        AlertDialog(
            onDismissRequest = viewModel::cancelDelete,
            title = { Text("Delete Insurance?") },
            text = { Text("This policy record will be permanently deleted.") },
            confirmButton = {
                TextButton(onClick = viewModel::confirmDelete) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::cancelDelete) { Text("Cancel") }
            },
        )
    }

    when {
        state.isLoading -> LoadingScreen()
        !state.hasVehicles -> EmptyScreen(
            title = "No vehicles",
            description = "Add a vehicle first to manage insurance.",
        )
        state.active.isEmpty() && state.expired.isEmpty() -> EmptyScreen(
            title = "No insurance policies",
            description = "Tap + to add your first insurance policy.",
        )
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                if (state.active.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Active Policies", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(state.active, key = { it.insurance.id }) { item ->
                        InsuranceCard(
                            item = item,
                            dateFormatter = dateFormatter,
                            onDelete = { viewModel.requestDelete(item.insurance.id) },
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                if (state.expired.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Expired Policies",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(state.expired, key = { it.insurance.id }) { item ->
                        InsuranceCard(
                            item = item,
                            dateFormatter = dateFormatter,
                            isExpired = true,
                            onDelete = { viewModel.requestDelete(item.insurance.id) },
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun InsuranceCard(
    item: InsuranceWithVehicle,
    dateFormatter: SimpleDateFormat,
    isExpired: Boolean = false,
    onDelete: () -> Unit,
) {
    val ins = item.insurance
    val now = System.currentTimeMillis()
    val daysLeft = TimeUnit.MILLISECONDS.toDays(ins.endDate - now)
    val typeLabel = if (ins.type == "COMPREHENSIVE") "Comprehensive" else "3rd Party"

    val containerColor = if (isExpired)
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    else
        MaterialTheme.colorScheme.primaryContainer

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ins.companyName,
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isExpired)
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        textDecoration = if (isExpired) TextDecoration.LineThrough else null,
                    )
                    Text(
                        text = typeLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isExpired)
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        CocIcons.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    CocIcons.Car,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = item.vehicleLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    CocIcons.Calendar,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${dateFormatter.format(Date(ins.startDate))} — ${dateFormatter.format(Date(ins.endDate))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                )
            }

            if (!isExpired) {
                Spacer(modifier = Modifier.height(4.dp))
                val statusColor = when {
                    daysLeft <= 7 -> MaterialTheme.colorScheme.error
                    daysLeft <= 30 -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.primary
                }
                Text(
                    text = if (daysLeft >= 0) "$daysLeft days remaining" else "Expired",
                    style = MaterialTheme.typography.labelSmall,
                    color = statusColor,
                )
            }
        }
    }
}