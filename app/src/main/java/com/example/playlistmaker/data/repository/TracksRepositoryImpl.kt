package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    private val database = DatabaseMock

    override suspend fun searchTracks(expression: String): List<Track> {
        return database.searchTracks(expression)
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> = database.getTrackByNameAndArtist(track)
    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) = database.insertTrack(track.copy(playlistId = playlistId))
    override suspend fun deleteTrackFromPlaylist(track: Track) = database.insertTrack(track.copy(playlistId = 0))
    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) = database.insertTrack(track.copy(favorite = isFavorite))
    override fun deleteTracksByPlaylistId(playlistId: Long) = database.deleteTracksByPlaylistId(playlistId)
    override fun getFavoriteTracks(): Flow<List<Track>> = database.getFavoriteTracks()
}