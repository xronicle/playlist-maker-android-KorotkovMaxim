package com.example.playlistmaker.ui.navigation

enum class Screen {
    Main,
    Search,
    Settings;

    fun getRoute(): String {
        return this.toString().lowercase()
    }
}