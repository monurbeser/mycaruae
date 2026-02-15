package com.mycaruae.app.feature.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.MaintenanceEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.MaintenanceRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MaintenanceHistoryUiState(
    val records: List<MaintenanceEntity> = emptyList(),
    val totalCost: Double = 0.0,
    val isLoading: Boolean = true,
)

@HiltViewModel
class MaintenanceHistoryViewModel @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MaintenanceHistoryUiState())
    val uiState: StateFlow<MaintenanceHistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                combine(
                    maintenanceRepository.getHistory(vehicle.id),
                    maintenanceRepository.getTotalCost(vehicle.id),
                ) { records, totalCost ->
                    MaintenanceHistoryUiState(
                        records = records,
                        totalCost = totalCost ?: 0.0,
                        isLoading = false,
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteRecord(id: String) {
        viewModelScope.launch {
            maintenanceRepository.deleteRecord(id)
        }
    }
}