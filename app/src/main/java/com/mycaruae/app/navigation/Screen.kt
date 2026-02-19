package com.mycaruae.app.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Register : Screen("register")
    data object Login : Screen("login")
    data object ResetPassword : Screen("reset_password")
    data object VehicleAdd : Screen("vehicle_add")
    data object VehicleEdit : Screen("vehicle_edit")
    data object Dashboard : Screen("dashboard")
    data object Services : Screen("services")
    data object MileageEntry : Screen("mileage_entry")
    data object MileageHistory : Screen("mileage_history")
    data object MaintenanceAdd : Screen("maintenance_add")
    data object MaintenanceHistory : Screen("maintenance_history")
    data object MaintenanceDetail : Screen("maintenance_detail/{id}") {
        fun createRoute(id: String) = "maintenance_detail/$id"
    }
    data object Reminders : Screen("reminders")
    data object ReminderCreate : Screen("reminder_create")
    data object Insurance : Screen("insurance")
    data object InsuranceAdd : Screen("insurance_add")
    data object RegistrationDetail : Screen("registration_detail/{type}") {
        fun createRoute(type: String) = "registration_detail/$type"
    }
    data object RegistrationRenew : Screen("registration_renew/{type}") {
        fun createRoute(type: String) = "registration_renew/$type"
    }
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
}

val bottomNavScreens = listOf(
    Screen.Dashboard,
    Screen.Services,
    Screen.Reminders,
    Screen.Profile,
)