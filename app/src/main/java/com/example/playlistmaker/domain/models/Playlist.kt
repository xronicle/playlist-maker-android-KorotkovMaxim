package com.example.playlistmaker.domain.models

data class Playlist(
    val id: Long,
    val name: String,
    val description: String,
    val imageUri: String? = null,
    val tracks: List<Track> = emptyList()
)