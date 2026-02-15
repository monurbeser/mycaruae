package com.mycaruae.app.feature.splash

import androidx.lifecycle.ViewModel
import com.mycaruae.app.data.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    suspend fun isUserLoggedIn(): Boolean {
        return userPreferences.userData.first().isLoggedIn
    }
}