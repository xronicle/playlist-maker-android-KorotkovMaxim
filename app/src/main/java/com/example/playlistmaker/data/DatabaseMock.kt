package com.example.playlistmaker.data

import com.example.playlistmaker.domain.models.Playlist
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

object DatabaseMock {
    private val _favoriteTracks = MutableStateFlow<List<Track>>(emptyList())
    private val playlistMap = mutableMapOf<Long, MutableList<Track>>()

    fun getFavoriteTracks(): Flow<List<Track>> = _favoriteTracks.asStateFlow()

    fun toggleFavorite(track: Track) {
        val currentList = _favoriteTracks.value.toMutableList()
        val existing = currentList.find { it.id == track.id }
        if (existing != null) {
            currentList.remove(existing)
        } else {
            currentList.add(track.copy(favorite = true))
        }
        // Обновляем Flow, и все экраны мгновенно получают новый список
        _favoriteTracks.value = currentList
    }

    fun isFavorite(trackId: Long): Boolean = _favoriteTracks.value.any { it.id == trackId }

    // НОВАЯ ФУНКЦИЯ: Ищем трек по ID
    fun getTrackById(id: Long): Track? {
        return _favoriteTracks.value.find { it.id == id }
    }

    fun addTrackToPlaylist(track: Track, playlistId: Long) {
        playlistMap.getOrPut(playlistId) { mutableListOf() }.add(track)
    }

    fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) {
        playlistMap[playlistId]?.removeAll { it.id == trackId }
    }

    // ================= ПЛЕЙЛИСТЫ =================
    private val playlists = mutableListOf<Playlist>()

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        emit(playlists)
    }

    fun getPlaylist(playlistId: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == playlistId })
    }

    suspend fun addNewPlaylist(name: String, description: String) {
        val newPlaylist = Playlist(
            id = System.currentTimeMillis(),
            name = name,
            description = description,
            tracks = mutableListOf()
        )
        playlists.add(newPlaylist)
    }

    suspend fun deletePlaylistById(id: Long) {
        playlists.removeAll { it.id == id }
    }

    // ================= ИСТОРИЯ ПОИСКА =================
    private val searchHistory = mutableListOf<String>()

    fun getHistory(): List<String> = searchHistory.toList()

    fun addToHistory(word: String) {
        searchHistory.remove(word)
        searchHistory.add(0, word)
    }
}