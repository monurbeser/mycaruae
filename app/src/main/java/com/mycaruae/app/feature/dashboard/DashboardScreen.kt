package com.mycaruae.app.feature.dashboard

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.components.EmptyScreen
import com.mycaruae.app.ui.components.LoadingScreen
import com.mycaruae.app.ui.components.StatusCard
import com.mycaruae.app.ui.theme.CocIcons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    onNavigateToMileageEntry: () -> Unit,
    onNavigateToMaintenanceAdd: () -> Unit,
    onNavigateToReminderCreate: () -> Unit,
    onNavigateToVehicleAdd: () -> Unit = {},
    onNavigateToMileageHistory: () -> Unit = {},
    onNavigateToVehicleEdit: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CocTopBar(
                title = if (state.userName.isNotBlank()) "Hi, ${state.userName}" else "Dashboard",
                actionIcon = CocIcons.Settings,
            )
        },
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingScreen(modifier = Modifier.padding(padding))
            }
            !state.hasVehicle -> {
                EmptyScreen(
                    title = "No vehicle yet",
                    description = "Add your first vehicle to start tracking registration, inspection, and maintenance.",
                    actionLabel = "Add Vehicle",
                    onAction = onNavigateToVehicleAdd,
                    modifier = Modifier.padding(padding),
                )
            }
            else -> {
                DashboardContent(
                    state = state,
                    onMileageClick = onNavigateToMileageEntry,
                    onMileageHistoryClick = onNavigateToMileageHistory,
                    onMaintenanceClick = onNavigateToMaintenanceAdd,
                    onReminderClick = onNavigateToReminderCreate,
                    onEditClick = onNavigateToVehicleEdit,
                    modifier = Modifier.padding(padding),
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    state: DashboardUiState,
    onMileageClick: () -> Unit,
    onMileageHistoryClick: () -> Unit,
    onMaintenanceClick: () -> Unit,
    onReminderClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vehicle = state.vehicle ?: return
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Vehicle Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Photo if available
                val firstPhoto = vehicle.photoUris
                    ?.split(",")
                    ?.firstOrNull()
                    ?.trim()

                if (!firstPhoto.isNullOrBlank()) {
                    AsyncImage(
                        model = Uri.parse(firstPhoto),
                        contentDescription = "Vehicle photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Title row with edit button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${state.brandName} ${vehicle.modelId}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = CocIcons.Edit,
                            contentDescription = "Edit vehicle",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = CocIcons.Calendar,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${vehicle.year}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    )

                    if (!vehicle.plateNumber.isNullOrBlank()) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = CocIcons.Car,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = vehicle.plateNumber,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        )
                    }
                }

                if (vehicle.currentMileage > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CocIcons.Mileage,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "%,d km".format(vehicle.currentMileage),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Status Section
        Text(
            text = "Status",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Registration Status
        StatusCard(
            title = "Registration",
            daysRemaining = state.registrationDaysLeft,
            expiryDateFormatted = dateFormatter.format(Date(vehicle.registrationExpiry)),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Inspection Status
        StatusCard(
            title = "Inspection",
            daysRemaining = state.inspectionDaysLeft,
            expiryDateFormatted = dateFormatter.format(Date(vehicle.inspectionExpiry)),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Actions
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = onMileageClick,
                modifier = Modifier.weight(1f),
            ) {
                Icon(CocIcons.Mileage, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Mileage")
            }
            OutlinedButton(
                onClick = onMaintenanceClick,
                modifier = Modifier.weight(1f),
            ) {
                Icon(CocIcons.Maintenance, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Service")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = onReminderClick,
                modifier = Modifier.weight(1f),
            ) {
                Icon(CocIcons.Reminders, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reminder")
            }
            OutlinedButton(
                onClick = onMileageHistoryClick,
                modifier = Modifier.weight(1f),
            ) {
                Icon(CocIcons.ChevronRight, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("KM Log")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}