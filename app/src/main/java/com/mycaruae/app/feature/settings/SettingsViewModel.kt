package com.mycaruae.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val vehicleRepository: VehicleRepository,
    private val reminderRepository: ReminderRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val availableDays: List<Int> = UserPreferences.AVAILABLE_DAYS

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val userData = userPreferences.userData.first()
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

    fun toggleNotificationDay(day: Int) {
        _uiState.update {
            val newDays = if (day in it.notificationDays) {
                it.notificationDays - day
            } else {
                it.notificationDays + day
            }
            it.copy(notificationDays = newDays)
        }
    }

    fun saveNotificationPreferences() {
        viewModelScope.launch {
            val days = _uiState.value.notificationDays
            userPreferences.saveNotificationDays(days)

            // Regenerate auto-reminders with new preferences
            val userData = userPreferences.userData.first()
            val vehicle = vehicleRepository.getVehicle(userData.userId).first()
            if (vehicle != null) {
                reminderRepository.generateExpiryReminders(
                    vehicleId = vehicle.id,
                    expiryDate = vehicle.registrationExpiry,
                    type = "REGISTRATION",
                    title = "Registration",
                    notificationDays = days,
                )
                reminderRepository.generateExpiryReminders(
                    vehicleId = vehicle.id,
                    expiryDate = vehicle.inspectionExpiry,
                    type = "INSPECTION",
                    title = "Inspection",
                    notificationDays = days,
                )
            }

            _uiState.update { it.copy(isSaved = true) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}