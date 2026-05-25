package com.example.playlistmaker.data

import com.example.playlistmaker.domain.models.Playlist
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

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
        _favoriteTracks.value = currentList
    }

    fun isFavorite(trackId: Long): Boolean = _favoriteTracks.value.any { it.id == trackId }

    fun getTrackById(id: Long): Track? {
        return _favoriteTracks.value.find { it.id == id }
    }
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())

    fun getAllPlaylists(): Flow<List<Playlist>> = _playlists.asStateFlow()

    fun getPlaylist(playlistId: Long): Flow<Playlist?> = _playlists.map { list ->
        list.find { it.id == playlistId }
    }

    suspend fun addNewPlaylist(name: String, description: String, imageUri: String?) {
        val currentList = _playlists.value.toMutableList()
        val newPlaylist = Playlist(
            id = System.currentTimeMillis(),
            name = name,
            description = description,
            imageUri = imageUri
        )
        currentList.add(newPlaylist)
        _playlists.value = currentList
    }

    suspend fun deletePlaylistById(id: Long) {
        val currentList = _playlists.value.toMutableList()
        currentList.removeAll { it.id == id }
        _playlists.value = currentList
    }

    fun addTrackToPlaylist(track: Track, playlistId: Long) {
        val currentList = _playlists.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == playlistId }
        if (index != -1) {
            val oldPlaylist = currentList[index]
            if (oldPlaylist.tracks.none { it.id == track.id }) {
                val updatedTracks = oldPlaylist.tracks.toMutableList()
                updatedTracks.add(track)
                currentList[index] = oldPlaylist.copy(tracks = updatedTracks)
                _playlists.value = currentList
            }
        }
    }

    fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) {
        val currentList = _playlists.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == playlistId }
        if (index != -1) {
            val oldPlaylist = currentList[index]
            val updatedTracks = oldPlaylist.tracks.filter { it.id != trackId }
            currentList[index] = oldPlaylist.copy(tracks = updatedTracks)
            _playlists.value = currentList
        }
    }
    private val searchHistory = mutableListOf<String>()

    fun getHistory(): List<String> = searchHistory.toList()

    fun addToHistory(word: String) {
        searchHistory.remove(word)
        searchHistory.add(0, word)
    }
}