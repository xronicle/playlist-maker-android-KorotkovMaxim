package com.example.playlistmaker.ui.navigation

enum class Screen {
    Main,
    Search,
    Settings,
    Playlists,
    Favorites;

    fun getRoute(): String {
        return this.toString().lowercase()
    }
}