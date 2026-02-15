package com.mycaruae.app.feature.vehicle

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.BrandEntity
import com.mycaruae.app.data.database.entity.EmirateEntity
import com.mycaruae.app.data.database.entity.VehicleEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.BrandRepository
import com.mycaruae.app.data.repository.EmirateRepository
import com.mycaruae.app.data.repository.ReminderRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VehicleAddUiState(
    val currentStep: Int = 0,
    // Step 0: Brand
    val selectedBrandId: String = "",
    val selectedBrandName: String = "",
    // Step 1: Model (free text for MVP)
    val modelName: String = "",
    // Step 2: Year
    val year: String = "",
    // Step 3: Emirate
    val selectedEmirateId: String = "",
    val selectedEmirateName: String = "",
    // Step 4: Plate Number
    val plateNumber: String = "",
    // Step 5: Registration & Inspection expiry
    val registrationExpiry: Long = 0L,
    val inspectionExpiry: Long = 0L,
    val registrationExpiryText: String = "",
    val inspectionExpiryText: String = "",
    // Step 6: Photos + Color + Mileage (optional)
    val photoUris: List<Uri> = emptyList(),
    val color: String = "",
    val currentMileage: String = "",
    // Validation
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
)

@HiltViewModel
class VehicleAddViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val emirateRepository: EmirateRepository,
    private val reminderRepository: ReminderRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleAddUiState())
    val uiState: StateFlow<VehicleAddUiState> = _uiState.asStateFlow()

    val brands: StateFlow<List<BrandEntity>> = brandRepository.getAllBrands()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val emirates: StateFlow<List<EmirateEntity>> = emirateRepository.getAllEmirates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val totalSteps = 7

    fun nextStep() {
        val state = _uiState.value
        val error = validateCurrentStep(state)
        if (error != null) {
            _uiState.update { it.copy(error = error) }
            return
        }
        _uiState.update { it.copy(currentStep = it.currentStep + 1, error = null) }
    }

    fun previousStep() {
        _uiState.update {
            it.copy(currentStep = (it.currentStep - 1).coerceAtLeast(0), error = null)
        }
    }

    private fun validateCurrentStep(state: VehicleAddUiState): String? {
        return when (state.currentStep) {
            0 -> if (state.selectedBrandId.isBlank()) "Please select a brand" else null
            1 -> if (state.modelName.isBlank()) "Please enter the model name" else null
            2 -> {
                val year = state.year.toIntOrNull()
                when {
                    year == null -> "Please enter a valid year"
                    year < 1990 || year > 2026 -> "Year must be between 1990 and 2026"
                    else -> null
                }
            }
            3 -> if (state.selectedEmirateId.isBlank()) "Please select an emirate" else null
            4 -> if (state.plateNumber.isBlank()) "Please enter your plate number" else null
            5 -> when {
                state.registrationExpiry == 0L -> "Please set registration expiry date"
                state.inspectionExpiry == 0L -> "Please set inspection expiry date"
                else -> null
            }
            6 -> null // Optional step, no validation
            else -> null
        }
    }

    fun selectBrand(id: String, name: String) {
        _uiState.update { it.copy(selectedBrandId = id, selectedBrandName = name, error = null) }
    }

    fun onModelNameChange(name: String) {
        _uiState.update { it.copy(modelName = name, error = null) }
    }

    fun onYearChange(year: String) {
        if (year.length <= 4 && year.all { it.isDigit() }) {
            _uiState.update { it.copy(year = year, error = null) }
        }
    }

    fun selectEmirate(id: String, name: String) {
        _uiState.update { it.copy(selectedEmirateId = id, selectedEmirateName = name, error = null) }
    }

    fun onPlateNumberChange(plate: String) {
        _uiState.update { it.copy(plateNumber = plate, error = null) }
    }

    fun setRegistrationExpiry(millis: Long, displayText: String) {
        _uiState.update { it.copy(registrationExpiry = millis, registrationExpiryText = displayText, error = null) }
    }

    fun setInspectionExpiry(millis: Long, displayText: String) {
        _uiState.update { it.copy(inspectionExpiry = millis, inspectionExpiryText = displayText, error = null) }
    }

    fun addPhotoUri(uri: Uri) {
        _uiState.update {
            if (it.photoUris.size < 5) {
                it.copy(photoUris = it.photoUris + uri)
            } else {
                it.copy(error = "Maximum 5 photos allowed")
            }
        }
    }

    fun removePhotoUri(uri: Uri) {
        _uiState.update { it.copy(photoUris = it.photoUris - uri) }
    }

    fun onColorChange(color: String) {
        _uiState.update { it.copy(color = color) }
    }

    fun onMileageChange(mileage: String) {
        if (mileage.all { it.isDigit() }) {
            _uiState.update { it.copy(currentMileage = mileage) }
        }
    }

    fun saveVehicle() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val state = _uiState.value
            val userData = userPreferences.userData.first()
            val userId = userData.userId
            val now = System.currentTimeMillis()

            val photoUrisString = state.photoUris
                .takeIf { it.isNotEmpty() }
                ?.joinToString(",") { it.toString() }

            val vehicleId = vehicleRepository.generateId()

            val vehicle = VehicleEntity(
                id = vehicleId,
                userId = userId,
                brandId = state.selectedBrandId,
                modelId = state.modelName,
                year = state.year.toIntOrNull() ?: 2024,
                emirate = state.selectedEmirateId,
                registrationExpiry = state.registrationExpiry,
                inspectionExpiry = state.inspectionExpiry,
                vin = null,
                plateNumber = state.plateNumber.ifBlank { null },
                color = state.color.ifBlank { null },
                currentMileage = state.currentMileage.toIntOrNull() ?: 0,
                photoUris = photoUrisString,
                pendingSync = true,
                createdAt = now,
                updatedAt = now,
            )

            vehicleRepository.addVehicle(vehicle)

            // Auto-generate reminder chains based on user's notification preferences
            val notificationDays = userData.notificationDays
            reminderRepository.generateExpiryReminders(
                vehicleId = vehicleId,
                expiryDate = state.registrationExpiry,
                type = "REGISTRATION",
                title = "Registration",
                notificationDays = notificationDays,
            )
            reminderRepository.generateExpiryReminders(
                vehicleId = vehicleId,
                expiryDate = state.inspectionExpiry,
                type = "INSPECTION",
                title = "Inspection",
                notificationDays = notificationDays,
            )

            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }
}