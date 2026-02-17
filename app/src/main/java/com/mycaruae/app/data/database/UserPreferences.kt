package com.mycaruae.app.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

data class UserData(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val hasCompletedOnboarding: Boolean = false,
    val activeVehicleId: String = "",
    val notificationDays: Set<Int> = DEFAULT_NOTIFICATION_DAYS,
) {
    companion object {
        val AVAILABLE_NOTIFICATION_DAYS = listOf(60, 30, 14, 7, 3, 1)
        val DEFAULT_NOTIFICATION_DAYS = setOf(30, 14, 7, 3, 1)
    }
}

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private object Keys {
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
        val ACTIVE_VEHICLE_ID = stringPreferencesKey("active_vehicle_id")
        val NOTIFICATION_DAYS = stringPreferencesKey("notification_days")
    }

    val userData: Flow<UserData> = dataStore.data.map { prefs ->
        val daysString = prefs[Keys.NOTIFICATION_DAYS]
        val days = if (daysString.isNullOrBlank()) {
            UserData.DEFAULT_NOTIFICATION_DAYS
        } else {
            daysString.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
        }

        UserData(
            userId = prefs[Keys.USER_ID] ?: "",
            name = prefs[Keys.USER_NAME] ?: "",
            email = prefs[Keys.USER_EMAIL] ?: "",
            isLoggedIn = prefs[Keys.IS_LOGGED_IN] ?: false,
            hasCompletedOnboarding = prefs[Keys.HAS_COMPLETED_ONBOARDING] ?: false,
            activeVehicleId = prefs[Keys.ACTIVE_VEHICLE_ID] ?: "",
            notificationDays = days,
        )
    }

    suspend fun saveUser(userId: String, name: String, email: String) {
        dataStore.edit { prefs ->
            prefs[Keys.USER_ID] = userId
            prefs[Keys.USER_NAME] = name
            prefs[Keys.USER_EMAIL] = email
            prefs[Keys.IS_LOGGED_IN] = true
        }
    }

    suspend fun setOnboardingCompleted() {
        dataStore.edit { prefs ->
            prefs[Keys.HAS_COMPLETED_ONBOARDING] = true
        }
    }

    suspend fun setActiveVehicleId(vehicleId: String) {
        dataStore.edit { prefs ->
            prefs[Keys.ACTIVE_VEHICLE_ID] = vehicleId
        }
    }

    suspend fun saveNotificationDays(days: Set<Int>) {
        dataStore.edit { prefs ->
            prefs[Keys.NOTIFICATION_DAYS] = days.joinToString(",")
        }
    }

    suspend fun logout() {
        dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = false
        }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}