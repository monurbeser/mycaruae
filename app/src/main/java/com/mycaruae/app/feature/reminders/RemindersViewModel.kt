package com.mycaruae.app.feature.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycaruae.app.data.database.entity.ReminderEntity
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

data class RemindersUiState(
    val pendingReminders: List<ReminderEntity> = emptyList(),
    val completedReminders: List<ReminderEntity> = emptyList(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemindersUiState())
    val uiState: StateFlow<RemindersUiState> = _uiState.asStateFlow()

    init {
        loadReminders()
    }

    private fun loadReminders() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                reminderRepository.getAll(vehicle.id).collect { reminders ->
                    _uiState.update {
                        it.copy(
                            pendingReminders = reminders.filter { r -> r.status == "PENDING" },
                            completedReminders = reminders.filter { r -> r.status != "PENDING" },
                            isLoading = false,
                        )
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun completeReminder(reminder: ReminderEntity) {
        viewModelScope.launch {
            reminderRepository.completeReminder(reminder)
        }
    }

    fun dismissReminder(reminder: ReminderEntity) {
        viewModelScope.launch {
            reminderRepository.dismissReminder(reminder)
        }
    }
}