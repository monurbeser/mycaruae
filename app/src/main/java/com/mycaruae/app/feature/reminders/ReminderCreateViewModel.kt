package com.mycaruae.app.feature.reminders

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

data class ReminderCreateUiState(
    val selectedType: ReminderType = ReminderType.CUSTOM,
    val title: String = "",
    val dueDate: Long = 0L,
    val dueDateText: String = "",
    val dueMileage: String = "",
    val note: String = "",
    val notifyDaysBefore: Int = 7,
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
)

enum class ReminderType(val displayName: String) {
    REGISTRATION("Registration Expiry"),
    INSPECTION("Inspection Expiry"),
    OIL_CHANGE("Oil Change"),
    TIRE_ROTATION("Tire Rotation"),
    INSURANCE("Insurance Renewal"),
    CUSTOM("Custom"),
}

@HiltViewModel
class ReminderCreateViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val vehicleRepository: VehicleRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderCreateUiState())
    val uiState: StateFlow<ReminderCreateUiState> = _uiState.asStateFlow()

    private var vehicleId: String = ""

    val reminderTypes: List<ReminderType> = ReminderType.entries.toList()
    val notifyOptions: List<Int> = listOf(1, 3, 7, 14, 30)

    init {
        loadVehicle()
    }

    private fun loadVehicle() {
        viewModelScope.launch {
            val userId = userPreferences.userData.first().userId
            val vehicle = vehicleRepository.getVehicle(userId).first()
            if (vehicle != null) {
                vehicleId = vehicle.id
            }
        }
    }

    fun selectType(type: ReminderType) {
        _uiState.update {
            it.copy(
                selectedType = type,
                title = if (type != ReminderType.CUSTOM) type.displayName else it.title,
                error = null,
            )
        }
    }

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value, error = null) }
    }

    fun setDueDate(millis: Long, displayText: String) {
        _uiState.update { it.copy(dueDate = millis, dueDateText = displayText, error = null) }
    }

    fun onDueMileageChange(value: String) {
        if (value.all { it.isDigit() } && value.length <= 7) {
            _uiState.update { it.copy(dueMileage = value, error = null) }
        }
    }

    fun onNoteChange(value: String) {
        _uiState.update { it.copy(note = value) }
    }

    fun setNotifyDaysBefore(days: Int) {
        _uiState.update { it.copy(notifyDaysBefore = days) }
    }

    fun save() {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.update { it.copy(error = "Please enter a title") }
            return
        }
        if (state.dueDate == 0L && state.dueMileage.isBlank()) {
            _uiState.update { it.copy(error = "Please set a due date or mileage") }
            return
        }
        if (vehicleId.isBlank()) {
            _uiState.update { it.copy(error = "No vehicle found") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            reminderRepository.addReminder(
                vehicleId = vehicleId,
                type = state.selectedType.name,
                title = state.title,
                dueDate = if (state.dueDate > 0L) state.dueDate else null,
                dueMileage = state.dueMileage.toIntOrNull(),
                note = state.note.ifBlank { null },
                notificationDaysBefore = state.notifyDaysBefore,
            )
            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }
}