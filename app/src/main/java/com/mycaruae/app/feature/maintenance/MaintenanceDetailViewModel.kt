package com.mycaruae.app.feature.maintenance

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.MaintenanceEntity
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

data class MaintenanceDetailUiState(
    val record: MaintenanceEntity? = null,
    val typeName: String = "",
    val isLoading: Boolean = true,
)

@HiltViewModel
class MaintenanceDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val maintenanceRepository: MaintenanceRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val recordId: String = savedStateHandle.get<String>("id") ?: ""

    private val _uiState = MutableStateFlow(MaintenanceDetailUiState())
    val uiState: StateFlow<MaintenanceDetailUiState> = _uiState.asStateFlow()

    init {
        loadRecord()
    }

    private fun loadRecord() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                val records = maintenanceRepository.getHistory(vehicle.id).first()
                val record = records.find { it.id == recordId }
                val typeName = if (record != null) {
                    try {
                        MaintenanceType.valueOf(record.type).displayName
                    } catch (_: Exception) {
                        record.type
                    }
                } else ""

                _uiState.update {
                    it.copy(
                        record = record,
                        typeName = typeName,
                        isLoading = false,
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}