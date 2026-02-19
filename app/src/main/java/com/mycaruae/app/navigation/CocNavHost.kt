package com.mycaruae.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mycaruae.app.feature.auth.LoginScreen
import com.mycaruae.app.feature.auth.RegisterScreen
import com.mycaruae.app.feature.auth.ResetPasswordScreen
import com.mycaruae.app.feature.dashboard.DashboardScreen
import com.mycaruae.app.feature.insurance.InsuranceAddScreen
import com.mycaruae.app.feature.maintenance.MaintenanceAddScreen
import com.mycaruae.app.feature.maintenance.MaintenanceDetailScreen
import com.mycaruae.app.feature.mileage.MileageEntryScreen
import com.mycaruae.app.feature.mileage.MileageHistoryScreen
import com.mycaruae.app.feature.onboarding.OnboardingScreen
import com.mycaruae.app.feature.registration.RegistrationDetailScreen
import com.mycaruae.app.feature.registration.RegistrationRenewScreen
import com.mycaruae.app.feature.reminders.ReminderCreateScreen
import com.mycaruae.app.feature.reminders.RemindersScreen
import com.mycaruae.app.feature.services.ServicesScreen
import com.mycaruae.app.feature.settings.SettingsScreen
import com.mycaruae.app.feature.splash.SplashScreen
import com.mycaruae.app.feature.vehicle.VehicleAddScreen
import com.mycaruae.app.feature.vehicle.VehicleEditScreen

@Composable
fun CocNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier,
    ) {
        // --- Auth flow ---
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToVehicleAdd = {
                    navController.navigate(Screen.VehicleAdd.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToResetPassword = { navController.navigate(Screen.ResetPassword.route) },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }

        // --- Vehicle ---
        composable(Screen.VehicleAdd.route) {
            VehicleAddScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.VehicleAdd.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.VehicleEdit.route) {
            VehicleEditScreen(
                onNavigateBack = { navController.popBackStack() },
                onVehicleDeleted = {
                    navController.navigate(Screen.VehicleAdd.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
            )
        }

        // --- Main screens (bottom nav) ---
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToMileageEntry = { navController.navigate(Screen.MileageEntry.route) },
                onNavigateToMaintenanceAdd = { navController.navigate(Screen.MaintenanceAdd.route) },
                onNavigateToReminderCreate = { navController.navigate(Screen.ReminderCreate.route) },
                onNavigateToVehicleAdd = { navController.navigate(Screen.VehicleAdd.route) },
                onNavigateToMileageHistory = { navController.navigate(Screen.MileageHistory.route) },
                onNavigateToVehicleEdit = { navController.navigate(Screen.VehicleEdit.route) },
            )
        }
        composable(Screen.Services.route) {
            ServicesScreen(
                onNavigateToMaintenanceDetail = { id ->
                    navController.navigate(Screen.MaintenanceDetail.createRoute(id))
                },
                onNavigateToMaintenanceAdd = { navController.navigate(Screen.MaintenanceAdd.route) },
                onNavigateToInsuranceAdd = { navController.navigate(Screen.InsuranceAdd.route) },
            )
        }
        composable(Screen.Reminders.route) {
            RemindersScreen(
                onNavigateToCreate = { navController.navigate(Screen.ReminderCreate.route) },
            )
        }
        composable(Screen.Profile.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onLoggedOut = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }

        // --- Detail screens ---
        composable(Screen.MileageEntry.route) {
            MileageEntryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.MileageHistory.route) {
            MileageHistoryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.MaintenanceAdd.route) {
            MaintenanceAddScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.MaintenanceDetail.route) {
            MaintenanceDetailScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.ReminderCreate.route) {
            ReminderCreateScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.InsuranceAdd.route) {
            InsuranceAddScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.RegistrationDetail.route) {
            RegistrationDetailScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.RegistrationRenew.route) {
            RegistrationRenewScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onLoggedOut = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    }
}