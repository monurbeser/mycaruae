package com.mycaruae.app.feature.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.model.MaintenanceType
import com.mycaruae.app.data.repository.MaintenanceRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MaintenanceAddUiState(
    val selectedTypes: Set<MaintenanceType> = emptySet(),
    val description: String = "",
    val serviceDate: Long = 0L,
    val serviceDateText: String = "",
    val mileage: String = "",
    val cost: String = "",
    val serviceProvider: String = "",
    val currentMileage: Int = 0,
    val error: String? = null,
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
)

@HiltViewModel
class MaintenanceAddViewModel @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MaintenanceAddUiState())
    val uiState: StateFlow<MaintenanceAddUiState> = _uiState.asStateFlow()

    private var vehicleId: String = ""

    val maintenanceTypes = MaintenanceType.entries.toList()

    init {
        loadVehicle()
    }

    private fun loadVehicle() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                vehicleId = vehicle.id
                _uiState.update {
                    it.copy(
                        currentMileage = vehicle.currentMileage,
                        mileage = if (vehicle.currentMileage > 0) vehicle.currentMileage.toString() else "",
                        isLoading = false,
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "No vehicle found") }
            }
        }
    }

    fun toggleType(type: MaintenanceType) {
        _uiState.update {
            val newSet = if (type in it.selectedTypes) {
                it.selectedTypes - type
            } else {
                it.selectedTypes + type
            }
            it.copy(selectedTypes = newSet, error = null)
        }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun setServiceDate(millis: Long, displayText: String) {
        _uiState.update { it.copy(serviceDate = millis, serviceDateText = displayText, error = null) }
    }

    fun onMileageChange(value: String) {
        if (value.all { it.isDigit() } && value.length <= 7) {
            _uiState.update { it.copy(mileage = value, error = null) }
        }
    }

    fun onCostChange(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,6}(\\.\\d{0,2})?$"))) {
            _uiState.update { it.copy(cost = value, error = null) }
        }
    }

    fun onServiceProviderChange(value: String) {
        _uiState.update { it.copy(serviceProvider = value) }
    }

    fun save() {
        val state = _uiState.value

        if (state.selectedTypes.isEmpty()) {
            _uiState.update { it.copy(error = "Please select at least one service type") }
            return
        }
        if (state.serviceDate == 0L) {
            _uiState.update { it.copy(error = "Please select a service date") }
            return
        }
        val costValue = state.cost.toDoubleOrNull()
        if (costValue == null || costValue < 0) {
            _uiState.update { it.copy(error = "Please enter a valid cost") }
            return
        }

        val combinedType = state.selectedTypes.joinToString(",") { it.name }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            maintenanceRepository.addRecord(
                vehicleId = vehicleId,
                type = combinedType,
                description = state.description.ifBlank { null },
                serviceDate = state.serviceDate,
                mileageAtService = state.mileage.toIntOrNull() ?: 0,
                cost = costValue,
                serviceProvider = state.serviceProvider.ifBlank { null },
            )
            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }
}