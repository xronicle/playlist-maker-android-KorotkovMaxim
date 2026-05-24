package com.example.playlistmaker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember // ОЧЕНЬ ВАЖНЫЙ ИМПОРТ ДЛЯ РЕШЕНИЯ ПРОБЛЕМЫ
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlistmaker.ui.screens.favorites.FavoritesScreen
import com.example.playlistmaker.ui.screens.main.MainScreen
import com.example.playlistmaker.ui.screens.player.TrackDetailsScreen
import com.example.playlistmaker.ui.screens.playlists.NewPlaylistScreen
import com.example.playlistmaker.ui.screens.playlists.PlaylistsScreen
import com.example.playlistmaker.ui.screens.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.screens.search.SearchScreen
import com.example.playlistmaker.ui.screens.search.SearchState
import com.example.playlistmaker.ui.screens.search.SearchViewModel
import com.example.playlistmaker.ui.screens.settings.SettingsScreen
import com.example.playlistmaker.ui.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistHost() {
    val navController = rememberNavController()

    val searchViewModel: SearchViewModel = koinViewModel()
    val playlistsViewModel: PlaylistsViewModel = koinViewModel()
    val settingsViewModel: SettingsViewModel = koinViewModel()

    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
    val favoriteTracks by playlistsViewModel.favoriteList.collectAsState(initial = emptyList())

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main.getRoute(),
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Main.getRoute()) {
                MainScreen(
                    navigateToSearch = { navController.navigate(Screen.Search.getRoute()) },
                    navigateToPlaylists = { navController.navigate(Screen.Playlists.getRoute()) },
                    navigateToSettings = { navController.navigate(Screen.Settings.getRoute()) },
                    navigateToFavorites = { navController.navigate(Screen.Favorites.getRoute()) }
                )
            }

            composable(Screen.Search.getRoute()) {
                SearchScreen(
                    searchViewModel = searchViewModel,
                    isDarkTheme = isDarkTheme,
                    onClick = { index ->
                        if (index == null) {
                            navController.popBackStack()
                        } else {
                            val playerRoute = "track_details"
                            navController.navigate("$playerRoute/$index")
                        }
                    }
                )
            }

            composable(Screen.Playlists.getRoute()) {
                PlaylistsScreen(
                    playlistsViewModel = playlistsViewModel,
                    isDarkTheme = isDarkTheme,
                    addNewPlaylist = { navController.navigate("new_playlist") },
                    navigateToPlaylist = { playlistId ->
                    },
                    navigateBack = { navController.popBackStack() }
                )
            }

            composable("new_playlist") {
                NewPlaylistScreen(
                    playlistsViewModel = playlistsViewModel,
                    navigateBack = { navController.popBackStack() }
                )
            }

            // Плеер из поиска (остается на индексах)
            composable(
                route = "track_details/{trackIndex}",
                arguments = listOf(navArgument("trackIndex") { type = NavType.IntType })
            ) { backStackEntry ->
                val trackIndex = backStackEntry.arguments?.getInt("trackIndex") ?: 0
                val searchState = searchViewModel.searchScreenState.collectAsState().value

                if (searchState is SearchState.Success) {
                    val track = searchState.foundList.getOrNull(trackIndex)
                    if (track != null) {
                        TrackDetailsScreen(
                            track = track,
                            playlistsViewModel = playlistsViewModel,
                            navigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }

            // Плеер из избранного (теперь работает по ID и использует remember!)
            composable(
                route = "favorite_track_details/{trackId}",
                arguments = listOf(navArgument("trackId") { type = NavType.LongType })
            ) { backStackEntry ->
                val trackId = backStackEntry.arguments?.getLong("trackId") ?: 0L

                // Получаем трек один раз при входе
                val initialTrack = playlistsViewModel.getTrackById(trackId)

                // "Запоминаем" его, чтобы экран не пропал при снятии лайка
                val track = remember { initialTrack }

                if (track != null) {
                    TrackDetailsScreen(
                        track = track,
                        playlistsViewModel = playlistsViewModel,
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }

            composable(Screen.Favorites.getRoute()) {
                FavoritesScreen(
                    playlistsViewModel = playlistsViewModel,
                    isDarkTheme = isDarkTheme,
                    navigateBack = { navController.popBackStack() },
                    navigateToPlayer = { id ->
                        navController.navigate("favorite_track_details/$id")
                    }
                )
            }

            composable(Screen.Settings.getRoute()) {
                SettingsScreen(
                    settingsViewModel = settingsViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}