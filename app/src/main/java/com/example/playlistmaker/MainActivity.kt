package com.example.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.ui.Theme
import com.example.playlistmaker.ui.navigation.PlaylistHost
import com.example.playlistmaker.ui.screens.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.screens.search.SearchViewModel
import com.example.playlistmaker.ui.screens.settings.SettingsViewModel

class MainActivity : ComponentActivity() {

    private val searchViewModel by viewModels<SearchViewModel> {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel(
                    tracksRepository = Creator.getTracksRepository()
                ) as T
            }
        }
    }

    private val playlistsViewModel by viewModels<PlaylistsViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

            Theme(darkTheme = isDarkTheme) {
                PlaylistHost(
                    searchViewModel = searchViewModel,
                    playlistsViewModel = playlistsViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}