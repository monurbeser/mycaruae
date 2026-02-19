package com.mycaruae.app.feature.insurance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.InsuranceEntity
import com.mycaruae.app.data.database.entity.VehicleEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.BrandRepository
import com.mycaruae.app.data.repository.InsuranceRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InsuranceWithVehicle(
    val insurance: InsuranceEntity,
    val vehicleLabel: String,
)

data class InsuranceListUiState(
    val active: List<InsuranceWithVehicle> = emptyList(),
    val expired: List<InsuranceWithVehicle> = emptyList(),
    val isLoading: Boolean = true,
    val hasVehicles: Boolean = false,
    val pendingDeleteId: String? = null,
)

@HiltViewModel
class InsuranceListViewModel @Inject constructor(
    private val insuranceRepository: InsuranceRepository,
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsuranceListUiState())
    val uiState: StateFlow<InsuranceListUiState> = _uiState.asStateFlow()

    init {
        loadInsurance()
    }

    private fun loadInsurance() {
        viewModelScope.launch {
            val userData = userPreferences.userData.first()
            val vehicles = vehicleRepository.getAllVehicles(userData.userId).first()

            if (vehicles.isEmpty()) {
                _uiState.update { it.copy(isLoading = false, hasVehicles = false) }
                return@launch
            }

            val brands = brandRepository.getAllBrands().first()

            // Expire old policies
            insuranceRepository.expireOld()

            insuranceRepository.getAll().collect { allInsurance ->
                val vehicleMap = vehicles.associateBy { it.id }
                val items = allInsurance.mapNotNull { ins ->
                    val vehicle = vehicleMap[ins.vehicleId] ?: return@mapNotNull null
                    val brandName = brands.find { it.id == vehicle.brandId }?.name ?: vehicle.brandId
                    val plate = vehicle.plateNumber ?: ""
                    val label = "$brandName ${vehicle.modelId} ${vehicle.year}" +
                            if (plate.isNotBlank()) " • $plate" else ""
                    InsuranceWithVehicle(insurance = ins, vehicleLabel = label)
                }

                _uiState.update {
                    it.copy(
                        active = items.filter { i -> i.insurance.status == "ACTIVE" },
                        expired = items.filter { i -> i.insurance.status == "EXPIRED" },
                        isLoading = false,
                        hasVehicles = true,
                    )
                }
            }
        }
    }

    fun requestDelete(id: String) {
        _uiState.update { it.copy(pendingDeleteId = id) }
    }

    fun confirmDelete() {
        val id = _uiState.value.pendingDeleteId ?: return
        viewModelScope.launch {
            insuranceRepository.delete(id)
            _uiState.update { it.copy(pendingDeleteId = null) }
        }
    }

    fun cancelDelete() {
        _uiState.update { it.copy(pendingDeleteId = null) }
    }
}