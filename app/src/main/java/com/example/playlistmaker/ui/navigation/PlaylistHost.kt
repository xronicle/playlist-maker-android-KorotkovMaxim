package com.example.playlistmaker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlistmaker.ui.screens.favorites.FavoritesScreen
import com.example.playlistmaker.ui.screens.main.MainScreen
import com.example.playlistmaker.ui.screens.player.TrackDetailsScreen
import com.example.playlistmaker.ui.screens.playlist.PlaylistScreen
import com.example.playlistmaker.ui.screens.playlist.PlaylistViewModel
import com.example.playlistmaker.ui.screens.playlists.NewPlaylistScreen
import com.example.playlistmaker.ui.screens.playlists.PlaylistsScreen
import com.example.playlistmaker.ui.screens.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.screens.search.SearchScreen
import com.example.playlistmaker.ui.screens.search.SearchState
import com.example.playlistmaker.ui.screens.search.SearchViewModel
import com.example.playlistmaker.ui.screens.settings.SettingsScreen
import com.example.playlistmaker.ui.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
                        navController.navigate("playlist_details/$playlistId")
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

            composable(
                route = "favorite_track_details/{trackId}",
                arguments = listOf(navArgument("trackId") { type = NavType.LongType })
            ) { backStackEntry ->
                val trackId = backStackEntry.arguments?.getLong("trackId") ?: 0L

                val playlists by playlistsViewModel.playlists.collectAsState(initial = emptyList())

                val initialTrack = favoriteTracks.find { it.id == trackId }
                    ?: playlists.flatMap { it.tracks ?: emptyList() }.find { it.id == trackId }

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

            composable(
                route = "playlist_details/{playlistId}",
                arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
            ) { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L

                val playlistViewModel: PlaylistViewModel = koinViewModel { parametersOf(playlistId) }

                PlaylistScreen(
                    viewModel = playlistViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToTrack = { trackId ->
                        navController.navigate("favorite_track_details/$trackId")
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