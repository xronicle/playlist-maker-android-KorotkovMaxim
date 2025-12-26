package com.example.playlistmaker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playlistmaker.ui.screens.MainScreen
import com.example.playlistmaker.ui.screens.SearchScreen
import com.example.playlistmaker.ui.screens.SettingsScreen

@Composable
fun PlaylistHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Main.getRoute()) {
        composable(route = Screen.Main.getRoute()) {
            MainScreen(
                navigateToSearch = {
                    navController.navigate(Screen.Search.getRoute())
                },
                navigateToSettings = {
                    navController.navigate(Screen.Settings.getRoute())
                }
            )
        }
        composable(route = Screen.Search.getRoute()) {
            SearchScreen { navController.popBackStack() }
        }
        composable(route = Screen.Settings.getRoute()) {
            SettingsScreen { navController.popBackStack() }
        }
    }
}