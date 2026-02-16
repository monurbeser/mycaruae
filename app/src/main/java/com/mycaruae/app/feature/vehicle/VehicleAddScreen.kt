package com.mycaruae.app.feature.vehicle

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mycaruae.app.data.database.entity.BrandEntity
import com.mycaruae.app.data.database.entity.EmirateEntity
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.theme.CocIcons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VehicleAddScreen(
    onNavigateToDashboard: () -> Unit,
    viewModel: VehicleAddViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val brands by viewModel.brands.collectAsStateWithLifecycle()
    val emirates by viewModel.emirates.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onNavigateToDashboard()
    }

    Scaffold(
        topBar = {
            CocTopBar(
                title = "Add Your Vehicle",
                showBack = state.currentStep > 0,
                onBackClick = viewModel::previousStep,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
        ) {
            // Progress
            LinearProgressIndicator(
                progress = { (state.currentStep + 1).toFloat() / viewModel.totalSteps },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
            Text(
                text = "Step ${state.currentStep + 1} of ${viewModel.totalSteps}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Error
            if (state.error != null) {
                Text(
                    text = state.error!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            // Step content
            when (state.currentStep) {
                0 -> BrandStep(
                    brands = brands,
                    selectedId = state.selectedBrandId,
                    onSelect = viewModel::selectBrand,
                    modifier = Modifier.weight(1f),
                )
                1 -> ModelStep(
                    modelName = state.modelName,
                    onModelNameChange = viewModel::onModelNameChange,
                    brandName = state.selectedBrandName,
                    modifier = Modifier.weight(1f),
                )
                2 -> YearStep(
                    year = state.year,
                    onYearChange = viewModel::onYearChange,
                    modifier = Modifier.weight(1f),
                )
                3 -> EmirateStep(
                    emirates = emirates,
                    selectedId = state.selectedEmirateId,
                    onSelect = viewModel::selectEmirate,
                    modifier = Modifier.weight(1f),
                )
                4 -> PlateStep(
                    plateNumber = state.plateNumber,
                    onPlateChange = viewModel::onPlateNumberChange,
                    modifier = Modifier.weight(1f),
                )
                5 -> DateStep(
                    registrationExpiryText = state.registrationExpiryText,
                    inspectionExpiryText = state.inspectionExpiryText,
                    onRegistrationDatePicked = viewModel::setRegistrationExpiry,
                    onInspectionDatePicked = viewModel::setInspectionExpiry,
                    modifier = Modifier.weight(1f),
                )
                6 -> PhotosAndDetailsStep(
                    photoUris = state.photoUris,
                    onAddPhoto = viewModel::addPhotoUri,
                    onRemovePhoto = viewModel::removePhotoUri,
                    color = state.color,
                    mileage = state.currentMileage,
                    vin = state.vin,
                    onColorChange = viewModel::onColorChange,
                    onMileageChange = viewModel::onMileageChange,
                    onVinChange = viewModel::onVinChange,
                    modifier = Modifier.weight(1f),
                )
            }

            // Next / Save button
            if (state.currentStep < viewModel.totalSteps - 1) {
                Button(
                    onClick = viewModel::nextStep,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(52.dp),
                ) {
                    Text("Next", style = MaterialTheme.typography.labelLarge)
                }
            } else {
                Button(
                    onClick = viewModel::saveVehicle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(52.dp),
                    enabled = !state.isLoading,
                ) {
                    Text(
                        text = if (state.isLoading) "Saving..." else "Save Vehicle",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

// === STEP COMPOSABLES ===

@Composable
private fun BrandStep(
    brands: List<BrandEntity>,
    selectedId: String,
    onSelect: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Select your car brand",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(brands, key = { it.id }) { brand ->
                val isSelected = brand.id == selectedId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(brand.id, brand.name) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = CocIcons.Car,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = brand.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ModelStep(
    modelName: String,
    onModelNameChange: (String) -> Unit,
    brandName: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Enter your $brandName model",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = modelName,
            onValueChange = onModelNameChange,
            label = { Text("Model Name") },
            placeholder = { Text("e.g. Camry, Patrol, Tucson") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
    }
}

@Composable
private fun YearStep(
    year: String,
    onYearChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "What year is your car?",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = year,
            onValueChange = onYearChange,
            label = { Text("Year") },
            placeholder = { Text("e.g. 2022") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

@Composable
private fun EmirateStep(
    emirates: List<EmirateEntity>,
    selectedId: String,
    onSelect: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = "Select your emirate",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        emirates.forEach { emirate ->
            val isSelected = emirate.id == selectedId
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onSelect(emirate.id, emirate.nameEn) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = CocIcons.City,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = emirate.nameEn,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = emirate.trafficAuthority,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlateStep(
    plateNumber: String,
    onPlateChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Enter your plate number",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "As shown on your vehicle registration card",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = plateNumber,
            onValueChange = onPlateChange,
            label = { Text("Plate Number") },
            placeholder = { Text("e.g. A 12345") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateStep(
    registrationExpiryText: String,
    inspectionExpiryText: String,
    onRegistrationDatePicked: (Long, String) -> Unit,
    onInspectionDatePicked: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
    var showRegPicker by remember { mutableStateOf(false) }
    var showInspPicker by remember { mutableStateOf(false) }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = "Expiry Dates",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "When do your registration and inspection expire?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Registration Expiry", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = { showRegPicker = true },
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Icon(CocIcons.Calendar, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(registrationExpiryText.ifBlank { "Select date" })
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Inspection Expiry", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = { showInspPicker = true },
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Icon(CocIcons.Calendar, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(inspectionExpiryText.ifBlank { "Select date" })
        }
    }

    if (showRegPicker) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showRegPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        onRegistrationDatePicked(millis, dateFormatter.format(Date(millis)))
                    }
                    showRegPicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showRegPicker = false }) { Text("Cancel") }
            },
        ) { DatePicker(state = pickerState) }
    }

    if (showInspPicker) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showInspPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        onInspectionDatePicked(millis, dateFormatter.format(Date(millis)))
                    }
                    showInspPicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showInspPicker = false }) { Text("Cancel") }
            },
        ) { DatePicker(state = pickerState) }
    }
}

@Composable
private fun PhotosAndDetailsStep(
    photoUris: List<Uri>,
    onAddPhoto: (Uri) -> Unit,
    onRemovePhoto: (Uri) -> Unit,
    color: String,
    mileage: String,
    vin: String,
    onColorChange: (String) -> Unit,
    onMileageChange: (String) -> Unit,
    onVinChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let {
            // Take persistable permission so photo survives app restart
            try {
                context.contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) { /* Some providers don't support persistable */ }
            onAddPhoto(it)
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding(),
    ) {
        Text(
            text = "Photos & Details",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Optional — you can add these later",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Photo section
        Text("Vehicle Photos", style = MaterialTheme.typography.labelLarge)
        Text(
            text = "Up to 5 photos",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().height(240.dp),
        ) {
            items(photoUris.size) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                ) {
                    AsyncImage(
                        model = photoUris[index],
                        contentDescription = "Vehicle photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                    IconButton(
                        onClick = { onRemovePhoto(photoUris[index]) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(28.dp)
                            .padding(2.dp)
                            .background(MaterialTheme.colorScheme.errorContainer, CircleShape),
                    ) {
                        Icon(
                            imageVector = CocIcons.Close,
                            contentDescription = "Remove",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }

            if (photoUris.size < 5) {
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = CocIcons.Add,
                                contentDescription = "Add photo",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = "Add",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // VIN (optional)
        OutlinedTextField(
            value = vin,
            onValueChange = onVinChange,
            label = { Text("VIN (optional)") },
            placeholder = { Text("17-character Vehicle Identification Number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
            supportingText = { if (vin.isNotEmpty()) Text("${vin.length}/17") },
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Color
        OutlinedTextField(
            value = color,
            onValueChange = onColorChange,
            label = { Text("Color") },
            placeholder = { Text("e.g. White") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Mileage
        OutlinedTextField(
            value = mileage,
            onValueChange = onMileageChange,
            label = { Text("Current Mileage (km)") },
            placeholder = { Text("e.g. 45000") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}