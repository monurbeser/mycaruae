package com.mycaruae.app.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.mycaruae.app.ui.theme.CocIcons

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Dashboard, "Dashboard", CocIcons.Home),
    BottomNavItem(Screen.MaintenanceHistory, "Maintenance", CocIcons.Maintenance),
    BottomNavItem(Screen.Reminders, "Reminders", CocIcons.Reminders),
    BottomNavItem(Screen.Profile, "Profile", CocIcons.Profile),
)

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?,
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            popUpTo(Screen.Dashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
            )
        }
    }
}