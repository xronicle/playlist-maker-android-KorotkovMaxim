package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl : PlaylistsRepository {

    private val database = DatabaseMock

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> = database.getPlaylist(playlistId)

    override fun getAllPlaylists(): Flow<List<Playlist>> = database.getAllPlaylists()

    override suspend fun addNewPlaylist(name: String, description: String, imageUri: String?) =
        database.addNewPlaylist(name, description, imageUri)

    override suspend fun deletePlaylistById(id: Long) = database.deletePlaylistById(id)
}