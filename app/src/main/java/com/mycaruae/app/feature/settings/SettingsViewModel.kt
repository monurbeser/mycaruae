package com.mycaruae.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.datastore.UserData
import com.mycaruae.app.data.datastore.UserPreferences
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

data class SettingsUiState(
    val userName: String = "",
    val userEmail: String = "",
    val notificationDays: Set<Int> = emptySet(),
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false,
    val isSaved: Boolean = false,
    val appVersion: String = "1.0.0",
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val vehicleRepository: VehicleRepository,
    private val reminderRepository: ReminderRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val availableDays: List<Int> = UserData.AVAILABLE_NOTIFICATION_DAYS

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            userPreferences.userData.collect { userData ->
                _uiState.update {
                    it.copy(
                        userName = userData.name,
                        userEmail = userData.email,
                        notificationDays = userData.notificationDays,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun toggleNotificationDay(day: Int) {
        _uiState.update { state ->
            val newDays = if (day in state.notificationDays) {
                state.notificationDays - day
            } else {
                state.notificationDays + day
            }
            state.copy(notificationDays = newDays)
        }
    }

    fun saveNotificationPreferences() {
        viewModelScope.launch {
            val state = _uiState.value
            userPreferences.saveNotificationDays(state.notificationDays)

            // Regenerate auto-reminders for all vehicles
            val userData = userPreferences.userData.first()
            val vehicles = vehicleRepository.getAllVehicles(userData.userId).first()
            vehicles.forEach { vehicle ->
                reminderRepository.generateExpiryReminders(
                    vehicleId = vehicle.id,
                    expiryDate = vehicle.registrationExpiry,
                    type = "REGISTRATION",
                    title = "Registration",
                    notificationDays = state.notificationDays,
                )
                reminderRepository.generateExpiryReminders(
                    vehicleId = vehicle.id,
                    expiryDate = vehicle.inspectionExpiry,
                    type = "INSPECTION",
                    title = "Inspection",
                    notificationDays = state.notificationDays,
                )
            }

            _uiState.update { it.copy(isSaved = true) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAll()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }

    fun resetSaveState() {
        _uiState.update { it.copy(isSaved = false) }
    }
}