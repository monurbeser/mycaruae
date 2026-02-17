@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.mycaruae.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.MileageLogEntity
import com.mycaruae.app.data.database.entity.VehicleEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.BrandRepository
import com.mycaruae.app.data.repository.EmirateRepository
import com.mycaruae.app.data.repository.MileageRepository
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

data class VehicleDashData(
    val vehicle: VehicleEntity,
    val brandName: String,
    val emirateName: String,
    val registrationDaysLeft: Long,
    val inspectionDaysLeft: Long,
    val recentMileage: List<MileageLogEntity>,
)

data class DashboardUiState(
    val userName: String = "",
    val vehiclePages: List<VehicleDashData> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = true,
    val hasVehicle: Boolean = false,
    val isRefreshing: Boolean = false,
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val emirateRepository: EmirateRepository,
    private val mileageRepository: MileageRepository,
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

            val brands = brandRepository.getAllBrands().first()
            val emirates = emirateRepository.getAllEmirates().first()

            vehicleRepository.getAllVehicles(userData.userId).collect { vehicles ->
                if (vehicles.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            vehiclePages = emptyList(),
                            isLoading = false,
                            isRefreshing = false,
                            hasVehicle = false,
                        )
                    }
                    return@collect
                }

                val now = System.currentTimeMillis()
                val pages = vehicles.map { vehicle ->
                    val brand = brands.find { it.id == vehicle.brandId }
                    val emirate = emirates.find { it.id == vehicle.emirate }
                    val mileage = mileageRepository.getHistory(vehicle.id).first()

                    VehicleDashData(
                        vehicle = vehicle,
                        brandName = brand?.name ?: vehicle.brandId,
                        emirateName = emirate?.nameEn ?: vehicle.emirate,
                        registrationDaysLeft = TimeUnit.MILLISECONDS.toDays(vehicle.registrationExpiry - now),
                        inspectionDaysLeft = TimeUnit.MILLISECONDS.toDays(vehicle.inspectionExpiry - now),
                        recentMileage = mileage.take(10),
                    )
                }

                // Find active vehicle page index
                val activeId = userPreferences.userData.first().activeVehicleId
                val activeIndex = vehicles.indexOfFirst { it.id == activeId }.coerceAtLeast(0)

                _uiState.update {
                    it.copy(
                        vehiclePages = pages,
                        currentPage = activeIndex,
                        isLoading = false,
                        isRefreshing = false,
                        hasVehicle = true,
                    )
                }
            }
        }
    }

    fun onPageChanged(page: Int) {
        val pages = _uiState.value.vehiclePages
        if (page in pages.indices) {
            _uiState.update { it.copy(currentPage = page) }
            viewModelScope.launch {
                userPreferences.setActiveVehicleId(pages[page].vehicle.id)
            }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        loadDashboard()
    }
}