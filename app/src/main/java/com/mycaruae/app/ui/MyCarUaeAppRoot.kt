package com.mycaruae.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mycaruae.app.navigation.BottomNavBar
import com.mycaruae.app.navigation.CocNavHost
import com.mycaruae.app.navigation.Screen
import com.mycaruae.app.navigation.bottomNavScreens
import com.mycaruae.app.ui.theme.MyCarUaeTheme

@Composable
fun MyCarUaeAppRoot() {
    MyCarUaeTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val showBottomBar = currentRoute in bottomNavScreens.map { it.route }

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomNavBar(
                        navController = navController,
                        currentRoute = currentRoute,
                    )
                }
            },
        ) { innerPadding ->
            CocNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
