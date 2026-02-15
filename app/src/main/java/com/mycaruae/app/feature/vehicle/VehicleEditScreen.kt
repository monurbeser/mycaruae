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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mycaruae.app.data.database.entity.BrandEntity
import com.mycaruae.app.data.database.entity.EmirateEntity
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.components.LoadingScreen
import com.mycaruae.app.ui.theme.CocIcons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleEditScreen(
    onNavigateBack: () -> Unit,
    onVehicleDeleted: () -> Unit,
    viewModel: VehicleEditViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val brands by viewModel.brands.collectAsStateWithLifecycle()
    val emirates by viewModel.emirates.collectAsStateWithLifecycle()
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }

    var showRegPicker by remember { mutableStateOf(false) }
    var showInspPicker by remember { mutableStateOf(false) }
    var showBrandPicker by remember { mutableStateOf(false) }
    var showEmiratePicker by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri -> uri?.let { viewModel.addPhotoUri(it) } }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onNavigateBack()
    }
    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onVehicleDeleted()
    }

    // Delete confirmation dialog
    if (state.showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = viewModel::hideDeleteConfirm,
            title = { Text("Delete Vehicle?") },
            text = { Text("This will permanently delete your vehicle and all associated records (mileage, maintenance, reminders). This cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = viewModel::deleteVehicle,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = viewModel::hideDeleteConfirm) { Text("Cancel") }
            },
        )
    }

    Scaffold(
        topBar = {
            CocTopBar(
                title = "Edit Vehicle",
                showBack = true,
                onBackClick = onNavigateBack,
            )
        },
    ) { padding ->
        if (state.isLoading && state.vehicleId.isEmpty()) {
            LoadingScreen(modifier = Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
                    .imePadding(),
            ) {
                // Error
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }

                // Brand
                Text("Brand", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedButton(
                    onClick = { showBrandPicker = !showBrandPicker },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                ) {
                    Icon(CocIcons.Car, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(state.selectedBrandName.ifBlank { "Select brand" })
                }
                if (showBrandPicker) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(200.dp).padding(vertical = 8.dp),
                    ) {
                        items(brands, key = { it.id }) { brand ->
                            val isSelected = brand.id == state.selectedBrandId
                            Card(
                                modifier = Modifier.clickable {
                                    viewModel.selectBrand(brand.id, brand.name)
                                    showBrandPicker = false
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                ),
                            ) {
                                Text(
                                    text = brand.name,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Model
                OutlinedTextField(
                    value = state.modelName,
                    onValueChange = viewModel::onModelNameChange,
                    label = { Text("Model") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Year
                OutlinedTextField(
                    value = state.year,
                    onValueChange = viewModel::onYearChange,
                    label = { Text("Year") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Emirate
                Text("Emirate", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedButton(
                    onClick = { showEmiratePicker = !showEmiratePicker },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                ) {
                    Icon(CocIcons.City, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(state.selectedEmirateName.ifBlank { "Select emirate" })
                }
                if (showEmiratePicker) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        emirates.forEach { emirate ->
                            val isSelected = emirate.id == state.selectedEmirateId
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                                    .clickable {
                                        viewModel.selectEmirate(emirate.id, emirate.nameEn)
                                        showEmiratePicker = false
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                ),
                            ) {
                                Text(
                                    text = emirate.nameEn,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Plate Number
                OutlinedTextField(
                    value = state.plateNumber,
                    onValueChange = viewModel::onPlateNumberChange,
                    label = { Text("Plate Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Registration Expiry
                Text("Registration Expiry", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedButton(
                    onClick = { showRegPicker = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                ) {
                    Icon(CocIcons.Calendar, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(state.registrationExpiryText.ifBlank { "Select date" })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Inspection Expiry
                Text("Inspection Expiry", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedButton(
                    onClick = { showInspPicker = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                ) {
                    Icon(CocIcons.Calendar, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(state.inspectionExpiryText.ifBlank { "Select date" })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Color
                OutlinedTextField(
                    value = state.color,
                    onValueChange = viewModel::onColorChange,
                    label = { Text("Color (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mileage
                OutlinedTextField(
                    value = state.currentMileage,
                    onValueChange = viewModel::onMileageChange,
                    label = { Text("Current Mileage (km)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    suffix = { Text("km") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Photos
                Text("Photos", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = "Up to 5 photos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    state.photoUris.forEach { uri ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                            IconButton(
                                onClick = { viewModel.removePhotoUri(uri) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(24.dp)
                                    .background(MaterialTheme.colorScheme.errorContainer, CircleShape),
                            ) {
                                Icon(
                                    CocIcons.Close,
                                    contentDescription = "Remove",
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                    if (state.photoUris.size < 5) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    galleryLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                CocIcons.Add,
                                contentDescription = "Add photo",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Save button
                Button(
                    onClick = viewModel::save,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    enabled = !state.isLoading,
                ) {
                    Text(
                        text = if (state.isLoading) "Saving..." else "Save Changes",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Delete button
                OutlinedButton(
                    onClick = viewModel::showDeleteConfirm,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                    ),
                ) {
                    Icon(CocIcons.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete Vehicle", style = MaterialTheme.typography.labelLarge)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Date Pickers
    if (showRegPicker) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showRegPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        viewModel.setRegistrationExpiry(millis, dateFormatter.format(Date(millis)))
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
                        viewModel.setInspectionExpiry(millis, dateFormatter.format(Date(millis)))
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