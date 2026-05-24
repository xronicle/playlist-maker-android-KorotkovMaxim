package com.example.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.playlistmaker.ui.Theme
import com.example.playlistmaker.ui.navigation.PlaylistHost
import com.example.playlistmaker.ui.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel // Импорт Koin для Compose

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

            Theme(darkTheme = isDarkTheme) {
                PlaylistHost()
            }
        }
    }
}