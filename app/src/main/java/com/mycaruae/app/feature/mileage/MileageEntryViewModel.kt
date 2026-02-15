package com.mycaruae.app.feature.mileage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.MileageRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MileageEntryUiState(
    val currentMileage: Int = 0,
    val newMileage: String = "",
    val error: String? = null,
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
)

@HiltViewModel
class MileageEntryViewModel @Inject constructor(
    private val mileageRepository: MileageRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MileageEntryUiState())
    val uiState: StateFlow<MileageEntryUiState> = _uiState.asStateFlow()

    private var vehicleId: String = ""

    init {
        loadCurrentMileage()
    }

    private fun loadCurrentMileage() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                vehicleId = vehicle.id
                _uiState.update {
                    it.copy(
                        currentMileage = vehicle.currentMileage,
                        isLoading = false,
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "No vehicle found") }
            }
        }
    }

    fun onMileageChange(value: String) {
        if (value.all { it.isDigit() } && value.length <= 7) {
            _uiState.update { it.copy(newMileage = value, error = null) }
        }
    }

    fun saveMileage() {
        val state = _uiState.value
        val newKm = state.newMileage.toIntOrNull()

        if (newKm == null || newKm <= 0) {
            _uiState.update { it.copy(error = "Please enter a valid mileage") }
            return
        }
        if (newKm <= state.currentMileage) {
            _uiState.update { it.copy(error = "New mileage must be higher than current (${"%,d".format(state.currentMileage)} km)") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            mileageRepository.addEntry(vehicleId, newKm)
            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }
}