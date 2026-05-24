package com.example.playlistmaker.ui.screens.settings

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("playlist_maker_prefs", Context.MODE_PRIVATE)

    private val _isDarkTheme = MutableStateFlow(
        prefs.getBoolean("is_dark_theme", false)
    )
    val isDarkTheme = _isDarkTheme.asStateFlow()

    fun updateTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        prefs.edit().putBoolean("is_dark_theme", isDark).apply()
    }
}