package com.mycaruae.app.feature.mileage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.MileageLogEntity
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

data class MileageHistoryUiState(
    val entries: List<MileageLogEntity> = emptyList(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class MileageHistoryViewModel @Inject constructor(
    private val mileageRepository: MileageRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MileageHistoryUiState())
    val uiState: StateFlow<MileageHistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                mileageRepository.getHistory(vehicle.id).collect { entries ->
                    _uiState.update {
                        it.copy(entries = entries, isLoading = false)
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}