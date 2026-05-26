package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.database.AppDatabase
import com.example.playlistmaker.data.database.PlaylistDbConverter
import com.example.playlistmaker.data.database.entity.PlaylistEntity
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.models.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    private val dao = database.playlistDao()

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return dao.getAllPlaylistsWithTracks().map { list ->
            list.map { PlaylistDbConverter.map(it) }
        }
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return dao.getPlaylistWithTracksById(playlistId).map { entity ->
            entity?.let { PlaylistDbConverter.map(it) }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, imageUri: String?) {
        dao.insertPlaylist(
            PlaylistEntity(name = name, description = description, imageUri = imageUri)
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        dao.deletePlaylistById(id)
    }

}