package com.mycaruae.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.BrandEntity
import com.mycaruae.app.data.database.entity.VehicleEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.BrandRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

data class DashboardUiState(
    val userName: String = "",
    val vehicle: VehicleEntity? = null,
    val brandName: String = "",
    val registrationDaysLeft: Long = 0,
    val inspectionDaysLeft: Long = 0,
    val isLoading: Boolean = true,
    val hasVehicle: Boolean = false,
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            val userData = userPreferences.userData.first()
            _uiState.update { it.copy(userName = userData.name) }

            vehicleRepository.getVehicle(userData.userId).collect { vehicle ->
                if (vehicle != null) {
                    val brands = brandRepository.getAllBrands().first()
                    val brand = brands.find { it.id == vehicle.brandId }
                    val now = System.currentTimeMillis()
                    val regDays = TimeUnit.MILLISECONDS.toDays(vehicle.registrationExpiry - now)
                    val inspDays = TimeUnit.MILLISECONDS.toDays(vehicle.inspectionExpiry - now)

                    _uiState.update {
                        it.copy(
                            vehicle = vehicle,
                            brandName = brand?.name ?: "",
                            registrationDaysLeft = regDays,
                            inspectionDaysLeft = inspDays,
                            isLoading = false,
                            hasVehicle = true,
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, hasVehicle = false)
                    }
                }
            }
        }
    }
}