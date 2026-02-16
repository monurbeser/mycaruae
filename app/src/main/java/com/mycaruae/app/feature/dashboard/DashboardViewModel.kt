package com.mycaruae.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.MileageLogEntity
import com.mycaruae.app.data.database.entity.VehicleEntity
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.BrandRepository
import com.mycaruae.app.data.repository.MileageRepository
import com.mycaruae.app.data.repository.ReminderRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
    val recentMileage: List<MileageLogEntity> = emptyList(),
    val pendingReminders: Int = 0,
    val isLoading: Boolean = true,
    val hasVehicle: Boolean = false,
    val isRefreshing: Boolean = false,
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val brandRepository: BrandRepository,
    private val mileageRepository: MileageRepository,
    private val reminderRepository: ReminderRepository,
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

            vehicleRepository.getVehicle(userData.userId).flatMapLatest { vehicle ->
                if (vehicle != null) {
                    combine(
                        mileageRepository.getHistory(vehicle.id),
                        reminderRepository.getUpcoming(vehicle.id, 50),
                    ) { mileageList, reminders ->
                        Triple(vehicle, mileageList, reminders)
                    }
                } else {
                    flowOf(Triple(null, emptyList(), emptyList()))
                }
            }.collect { (vehicle, mileageList, reminders) ->
                if (vehicle != null) {
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
                            recentMileage = mileageList.take(10),
                            pendingReminders = reminders.size,
                            isLoading = false,
                            isRefreshing = false,
                            hasVehicle = true,
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, isRefreshing = false, hasVehicle = false)
                    }
                }
            }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        // Flow zaten reactive, sadece flag reset
        viewModelScope.launch {
            kotlinx.coroutines.delay(500) // Minimum visual feedback
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}