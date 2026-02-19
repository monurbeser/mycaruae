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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class VehicleEditUiState(
    val vehicleId: String = "",
    val selectedBrandId: String = "",
    val selectedBrandName: String = "",
    val modelName: String = "",
    val year: String = "",
    val selectedEmirateId: String = "",
    val selectedEmirateName: String = "",
    val plateNumber: String = "",
    val registrationExpiry: Long = 0L,
    val inspectionExpiry: Long = 0L,
    val registrationExpiryText: String = "",
    val inspectionExpiryText: String = "",
    val originalRegistrationExpiry: Long = 0L,
    val originalInspectionExpiry: Long = 0L,
    val photoUris: List<Uri> = emptyList(),
    val color: String = "",
    val currentMileage: String = "",
    val error: String? = null,
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false,
    val hasRemainingVehicles: Boolean = false,
    val showDeleteConfirm: Boolean = false,
)

@HiltViewModel
class VehicleEditViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val emirateRepository: EmirateRepository,
    private val reminderRepository: ReminderRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleEditUiState())
    val uiState: StateFlow<VehicleEditUiState> = _uiState.asStateFlow()

    val brands: StateFlow<List<BrandEntity>> = brandRepository.getAllBrands()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val emirates: StateFlow<List<EmirateEntity>> = emirateRepository.getAllEmirates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    init {
        loadVehicle()
    }

    private fun loadVehicle() {
        viewModelScope.launch {
            val userData = userPreferences.userData.first()
            val activeId = userData.activeVehicleId

            // Load active vehicle or fallback to first
            val vehicle = if (activeId.isNotBlank()) {
                vehicleRepository.getVehicleByIdOnce(activeId)
            } else {
                vehicleRepository.getVehicle(userData.userId).first()
            }

            val allBrands = brandRepository.getAllBrands().first()
            val allEmirates = emirateRepository.getAllEmirates().first()

            if (vehicle != null) {
                val brand = allBrands.find { it.id == vehicle.brandId }
                val emirate = allEmirates.find { it.id == vehicle.emirate }
                val photos = vehicle.photoUris
                    ?.split(",")
                    ?.filter { it.isNotBlank() }
                    ?.map { Uri.parse(it.trim()) }
                    ?: emptyList()

                _uiState.update {
                    it.copy(
                        vehicleId = vehicle.id,
                        selectedBrandId = vehicle.brandId,
                        selectedBrandName = brand?.name ?: "",
                        modelName = vehicle.modelId,
                        year = vehicle.year.toString(),
                        selectedEmirateId = vehicle.emirate,
                        selectedEmirateName = emirate?.nameEn ?: "",
                        plateNumber = vehicle.plateNumber ?: "",
                        registrationExpiry = vehicle.registrationExpiry,
                        inspectionExpiry = vehicle.inspectionExpiry,
                        registrationExpiryText = dateFormatter.format(Date(vehicle.registrationExpiry)),
                        inspectionExpiryText = dateFormatter.format(Date(vehicle.inspectionExpiry)),
                        originalRegistrationExpiry = vehicle.registrationExpiry,
                        originalInspectionExpiry = vehicle.inspectionExpiry,
                        photoUris = photos,
                        color = vehicle.color ?: "",
                        currentMileage = if (vehicle.currentMileage > 0) vehicle.currentMileage.toString() else "",
                        isLoading = false,
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "No vehicle found") }
            }
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
            if (it.photoUris.size < 5) it.copy(photoUris = it.photoUris + uri)
            else it.copy(error = "Maximum 5 photos allowed")
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

    fun showDeleteConfirm() {
        _uiState.update { it.copy(showDeleteConfirm = true) }
    }

    fun hideDeleteConfirm() {
        _uiState.update { it.copy(showDeleteConfirm = false) }
    }

    fun save() {
        val state = _uiState.value

        if (state.selectedBrandId.isBlank()) { _uiState.update { it.copy(error = "Please select a brand") }; return }
        if (state.modelName.isBlank()) { _uiState.update { it.copy(error = "Please enter the model") }; return }
        val yearInt = state.year.toIntOrNull()
        if (yearInt == null || yearInt < 1990 || yearInt > 2026) { _uiState.update { it.copy(error = "Year must be 1990-2026") }; return }
        if (state.selectedEmirateId.isBlank()) { _uiState.update { it.copy(error = "Please select an emirate") }; return }
        if (state.plateNumber.isBlank()) { _uiState.update { it.copy(error = "Please enter plate number") }; return }
        if (state.registrationExpiry == 0L) { _uiState.update { it.copy(error = "Please set registration expiry") }; return }
        if (state.inspectionExpiry == 0L) { _uiState.update { it.copy(error = "Please set inspection expiry") }; return }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val userData = userPreferences.userData.first()
            val now = System.currentTimeMillis()
            val photoUrisString = state.photoUris
                .takeIf { it.isNotEmpty() }
                ?.joinToString(",") { it.toString() }

            val updated = VehicleEntity(
                id = state.vehicleId,
                userId = userData.userId,
                brandId = state.selectedBrandId,
                modelId = state.modelName,
                year = yearInt,
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
            vehicleRepository.updateVehicle(updated)

            val notificationDays = userData.notificationDays
            if (state.registrationExpiry != state.originalRegistrationExpiry) {
                reminderRepository.generateExpiryReminders(
                    vehicleId = state.vehicleId,
                    expiryDate = state.registrationExpiry,
                    type = "REGISTRATION",
                    title = "Registration",
                    notificationDays = notificationDays,
                )
            }
            if (state.inspectionExpiry != state.originalInspectionExpiry) {
                reminderRepository.generateExpiryReminders(
                    vehicleId = state.vehicleId,
                    expiryDate = state.inspectionExpiry,
                    type = "INSPECTION",
                    title = "Inspection",
                    notificationDays = notificationDays,
                )
            }

            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }

    fun deleteVehicle() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, showDeleteConfirm = false) }
            val vehicleId = _uiState.value.vehicleId
            val userData = userPreferences.userData.first()

            vehicleRepository.deleteVehicle(vehicleId)

            // Check remaining vehicles
            val remaining = vehicleRepository.getAllVehicles(userData.userId).first()
            if (remaining.isNotEmpty()) {
                // Set first remaining as active
                userPreferences.setActiveVehicleId(remaining.first().id)
                _uiState.update {
                    it.copy(isLoading = false, isDeleted = true, hasRemainingVehicles = true)
                }
            } else {
                userPreferences.setActiveVehicleId("")
                _uiState.update {
                    it.copy(isLoading = false, isDeleted = true, hasRemainingVehicles = false)
                }
            }
        }
    }
}