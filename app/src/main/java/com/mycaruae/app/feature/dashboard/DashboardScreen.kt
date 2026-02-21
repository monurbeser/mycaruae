package com.mycaruae.app.feature.dashboard

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mycaruae.app.data.database.entity.MileageLogEntity
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.components.EmptyScreen
import com.mycaruae.app.ui.components.LoadingScreen
import com.mycaruae.app.ui.components.StatusCard
import com.mycaruae.app.ui.theme.Amber80
import com.mycaruae.app.ui.theme.CocIcons
import com.mycaruae.app.ui.theme.Navy10
import com.mycaruae.app.ui.theme.Navy20
import com.mycaruae.app.ui.theme.Navy30
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Premium header
        CocTopBar(
            title = if (state.userName.isNotBlank()) "Hi, ${state.userName}" else "Dashboard",
            actionIcon = CocIcons.Settings,
        )

        when {
            state.isLoading -> LoadingScreen()
            !state.hasVehicle -> {
                EmptyScreen(
                    title = "No vehicle yet",
                    description = "Add your first vehicle to start tracking registration, inspection, and maintenance.",
                    actionLabel = "Add Vehicle",
                    onAction = onNavigateToVehicleAdd,
                )
            }
            else -> {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = viewModel::refresh,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    DashboardPager(
                        state = state,
                        onPageChanged = viewModel::onPageChanged,
                        onMileageClick = onNavigateToMileageEntry,
                        onMileageHistoryClick = onNavigateToMileageHistory,
                        onMaintenanceClick = onNavigateToMaintenanceAdd,
                        onReminderClick = onNavigateToReminderCreate,
                        onEditClick = onNavigateToVehicleEdit,
                        onAddVehicle = onNavigateToVehicleAdd,
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardPager(
    state: DashboardUiState,
    onPageChanged: (Int) -> Unit,
    onMileageClick: () -> Unit,
    onMileageHistoryClick: () -> Unit,
    onMaintenanceClick: () -> Unit,
    onReminderClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddVehicle: () -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.vehiclePages.size },
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            onPageChanged(page)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (state.vehiclePages.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                state.vehiclePages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (index == pagerState.currentPage) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.outlineVariant,
                            ),
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            VehiclePage(
                data = state.vehiclePages[page],
                onMileageClick = onMileageClick,
                onMileageHistoryClick = onMileageHistoryClick,
                onMaintenanceClick = onMaintenanceClick,
                onReminderClick = onReminderClick,
                onEditClick = onEditClick,
                onAddVehicle = onAddVehicle,
            )
        }
    }
}

@Composable
private fun VehiclePage(
    data: VehicleDashData,
    onMileageClick: () -> Unit,
    onMileageHistoryClick: () -> Unit,
    onMaintenanceClick: () -> Unit,
    onReminderClick: () -> Unit,
    onEditClick: () -> Unit,
    onAddVehicle: () -> Unit,
) {
    val vehicle = data.vehicle
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // ── Vehicle Hero Card ──
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column {
                // Photo
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
                            .height(180.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    // Title + edit
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${data.brandName} ${vehicle.modelId}",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = "${vehicle.year} • ${data.emirateName}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        IconButton(onClick = onEditClick) {
                            Icon(
                                imageVector = CocIcons.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Info chips row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (!vehicle.plateNumber.isNullOrBlank()) {
                            InfoChip(
                                icon = CocIcons.Car,
                                text = vehicle.plateNumber,
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (vehicle.currentMileage > 0) {
                            InfoChip(
                                icon = CocIcons.Mileage,
                                text = "%,d km".format(vehicle.currentMileage),
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (!vehicle.color.isNullOrBlank()) {
                            InfoChip(
                                icon = null,
                                text = vehicle.color,
                                prefix = "🎨",
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Expiry Status ──
        Text(
            text = "Expiry Status",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(10.dp))

        StatusCard(
            title = "Registration",
            daysRemaining = data.registrationDaysLeft,
            expiryDateFormatted = dateFormatter.format(Date(vehicle.registrationExpiry)),
        )
        Spacer(modifier = Modifier.height(8.dp))
        StatusCard(
            title = "Inspection",
            daysRemaining = data.inspectionDaysLeft,
            expiryDateFormatted = dateFormatter.format(Date(vehicle.inspectionExpiry)),
        )

        // ── Mileage Chart ──
        if (data.recentMileage.size >= 2) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Mileage Trend",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(10.dp))
            MileageChart(
                entries = data.recentMileage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Quick Actions ──
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            QuickActionCard(
                icon = CocIcons.Mileage,
                label = "Mileage",
                onClick = onMileageClick,
                modifier = Modifier.weight(1f),
            )
            QuickActionCard(
                icon = CocIcons.Maintenance,
                label = "Service",
                onClick = onMaintenanceClick,
                modifier = Modifier.weight(1f),
            )
            QuickActionCard(
                icon = CocIcons.Reminders,
                label = "Remind",
                onClick = onReminderClick,
                modifier = Modifier.weight(1f),
            )
            QuickActionCard(
                icon = CocIcons.ChevronRight,
                label = "KM Log",
                onClick = onMileageHistoryClick,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Add Vehicle
        FilledTonalButton(
            onClick = onAddVehicle,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(CocIcons.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Vehicle")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    text: String,
    modifier: Modifier = Modifier,
    prefix: String? = null,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (prefix != null) {
                Text(text = prefix, style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.width(4.dp))
            }
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun MileageChart(
    entries: List<MileageLogEntity>,
    modifier: Modifier = Modifier,
) {
    val sorted = entries.sortedBy { it.recordedDate }
    val lineColor = MaterialTheme.colorScheme.primary
    val dotColor = MaterialTheme.colorScheme.primary
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp)
    val dateFormatter = SimpleDateFormat("dd/MM", Locale.ENGLISH)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
        ) {
            if (sorted.size < 2) return@Canvas

            val values = sorted.map { it.mileage.toFloat() }
            val minVal = values.min()
            val maxVal = values.max()
            val range = if (maxVal - minVal > 0f) maxVal - minVal else 1f

            val leftPadding = 60f
            val bottomPadding = 36f
            val topPadding = 8f
            val chartWidth = size.width - leftPadding - 8f
            val chartHeight = size.height - bottomPadding - topPadding

            for (i in 0..2) {
                val y = topPadding + chartHeight * (1f - i / 2f)
                drawLine(
                    color = gridColor,
                    start = Offset(leftPadding, y),
                    end = Offset(size.width - 8f, y),
                    strokeWidth = 1f,
                )
                val labelVal = (minVal + range * i / 2f).toInt()
                val text = "%,d".format(labelVal)
                val result = textMeasurer.measure(text, labelStyle)
                drawText(
                    textLayoutResult = result,
                    color = labelColor,
                    topLeft = Offset(
                        leftPadding - result.size.width - 6f,
                        y - result.size.height / 2f,
                    ),
                )
            }

            val path = Path()
            val points = values.mapIndexed { index, value ->
                val x = leftPadding + (index.toFloat() / (values.size - 1)) * chartWidth
                val y = topPadding + chartHeight * (1f - (value - minVal) / range)
                Offset(x, y)
            }

            points.forEachIndexed { index, point ->
                if (index == 0) path.moveTo(point.x, point.y)
                else path.lineTo(point.x, point.y)
            }

            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round),
            )

            points.forEach { point ->
                drawCircle(color = dotColor, radius = 5f, center = point)
            }

            val labelIndices = listOf(0, sorted.size - 1).distinct()
            labelIndices.forEach { idx ->
                val x = leftPadding + (idx.toFloat() / (values.size - 1)) * chartWidth
                val dateText = dateFormatter.format(Date(sorted[idx].recordedDate))
                val result = textMeasurer.measure(dateText, labelStyle)
                drawText(
                    textLayoutResult = result,
                    color = labelColor,
                    topLeft = Offset(
                        (x - result.size.width / 2f).coerceIn(leftPadding, size.width - result.size.width.toFloat()),
                        topPadding + chartHeight + 10f,
                    ),
                )
            }
        }
    }
}