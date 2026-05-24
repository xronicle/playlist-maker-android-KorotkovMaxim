package com.example.playlistmaker.ui.navigation

enum class Screen {
    Main,
    Search,
    Settings,
    Playlists,
    Favorites,
    NewPlaylist,
    TrackDetails;

    fun getRoute(): String {
        return this.toString().lowercase()
    }
}