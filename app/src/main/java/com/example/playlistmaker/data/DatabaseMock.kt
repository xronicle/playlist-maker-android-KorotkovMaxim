package com.example.playlistmaker.data

import com.example.playlistmaker.domain.models.Playlist
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

object DatabaseMock {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()

    private val tracks = mutableListOf<Track>(
        Track(1, "Smells Like Teen Spirit", "Nirvana", "5:01", "", false, 0),
        Track(2, "Billie Jean", "Michael Jackson", "4:54", "", false, 0),
        Track(3, "Bohemian Rhapsody", "Queen", "5:55", "", false, 0)
    )

    fun getHistory(): List<String> = historyList.toList()

    fun addToHistory(word: String) {
        historyList.remove(word)
        historyList.add(0, word)
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500)
        val filteredPlaylists = mutableListOf<Playlist>()

        playlists.forEach { playlist ->
            val playlistTracks = tracks.filter { track -> track.playlistId == playlist.id }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }

        emit(filteredPlaylists.toList())
        delay(100)
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == id })
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.id == trackId }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300)
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
    }

    fun searchTracks(expression: String): List<Track> {
        println("ПОИСК: Ищем '$expression' в базе из ${tracks.size} треков")
        val result = tracks.filter { it.trackName.contains(expression, ignoreCase = true) }
        println("ПОИСК: Найдено ${result.size} треков")
        return result
    }


}