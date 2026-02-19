package com.mycaruae.app.feature.insurance

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.InsuranceEntity
import com.mycaruae.app.data.database.entity.VehicleEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.BrandRepository
import com.mycaruae.app.data.repository.InsuranceRepository
import com.mycaruae.app.data.repository.ReminderRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VehicleOption(
    val id: String,
    val label: String,
)

data class InsuranceAddUiState(
    val vehicles: List<VehicleOption> = emptyList(),
    val selectedVehicleId: String = "",
    val selectedVehicleLabel: String = "",
    val companyName: String = "",
    val insuranceType: String = "THIRD_PARTY",
    val startDate: Long = 0L,
    val startDateText: String = "",
    val endDate: Long = 0L,
    val endDateText: String = "",
    val documentUri: Uri? = null,
    val error: String? = null,
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
)

@HiltViewModel
class InsuranceAddViewModel @Inject constructor(
    private val insuranceRepository: InsuranceRepository,
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val reminderRepository: ReminderRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsuranceAddUiState())
    val uiState: StateFlow<InsuranceAddUiState> = _uiState.asStateFlow()

    init {
        loadVehicles()
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            val userData = userPreferences.userData.first()
            val vehicles = vehicleRepository.getAllVehicles(userData.userId).first()
            val brands = brandRepository.getAllBrands().first()

            val options = vehicles.map { v ->
                val brandName = brands.find { it.id == v.brandId }?.name ?: v.brandId
                val plate = v.plateNumber ?: ""
                val label = "$brandName ${v.modelId} ${v.year}" +
                        if (plate.isNotBlank()) " • $plate" else ""
                VehicleOption(id = v.id, label = label)
            }

            // Auto-select active vehicle
            val activeId = userData.activeVehicleId
            val activeOption = options.find { it.id == activeId } ?: options.firstOrNull()

            _uiState.update {
                it.copy(
                    vehicles = options,
                    selectedVehicleId = activeOption?.id ?: "",
                    selectedVehicleLabel = activeOption?.label ?: "",
                    isLoading = false,
                )
            }
        }
    }

    fun selectVehicle(id: String, label: String) {
        _uiState.update { it.copy(selectedVehicleId = id, selectedVehicleLabel = label, error = null) }
    }

    fun onCompanyChange(name: String) {
        _uiState.update { it.copy(companyName = name, error = null) }
    }

    fun setInsuranceType(type: String) {
        _uiState.update { it.copy(insuranceType = type, error = null) }
    }

    fun setStartDate(millis: Long, text: String) {
        _uiState.update { it.copy(startDate = millis, startDateText = text, error = null) }
    }

    fun setEndDate(millis: Long, text: String) {
        _uiState.update { it.copy(endDate = millis, endDateText = text, error = null) }
    }

    fun setDocumentUri(uri: Uri?) {
        _uiState.update { it.copy(documentUri = uri) }
    }

    fun save() {
        val state = _uiState.value
        if (state.selectedVehicleId.isBlank()) { _uiState.update { it.copy(error = "Please select a vehicle") }; return }
        if (state.companyName.isBlank()) { _uiState.update { it.copy(error = "Please enter insurance company") }; return }
        if (state.startDate == 0L) { _uiState.update { it.copy(error = "Please set start date") }; return }
        if (state.endDate == 0L) { _uiState.update { it.copy(error = "Please set end date") }; return }
        if (state.endDate <= state.startDate) { _uiState.update { it.copy(error = "End date must be after start date") }; return }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val now = System.currentTimeMillis()
            val id = insuranceRepository.generateId()

            val insurance = InsuranceEntity(
                id = id,
                vehicleId = state.selectedVehicleId,
                companyName = state.companyName.trim(),
                type = state.insuranceType,
                startDate = state.startDate,
                endDate = state.endDate,
                documentUri = state.documentUri?.toString(),
                status = if (state.endDate > now) "ACTIVE" else "EXPIRED",
                pendingSync = true,
                createdAt = now,
                updatedAt = now,
            )
            insuranceRepository.add(insurance)

            // Generate insurance expiry reminders
            val userData = userPreferences.userData.first()
            reminderRepository.generateExpiryReminders(
                vehicleId = state.selectedVehicleId,
                expiryDate = state.endDate,
                type = "INSURANCE",
                title = "Insurance",
                notificationDays = userData.notificationDays,
            )

            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }
}