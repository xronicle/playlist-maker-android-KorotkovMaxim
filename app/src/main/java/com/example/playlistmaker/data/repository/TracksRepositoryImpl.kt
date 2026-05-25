package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.TracksSearchRequest
import com.example.playlistmaker.data.dto.TracksSearchResponse
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Resource
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }
            200 -> {
                val tracks = (response as TracksSearchResponse).results.map { dto ->
                    Track(
                        id = dto.trackId,
                        trackName = dto.trackName,
                        artistName = dto.artistName,
                        trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(dto.trackTimeMillis),
                        image = dto.artworkUrl100 ?: "",
                        favorite = false,
                        playlistId = 0L
                    )
                }
                emit(Resource.Success(tracks))
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        DatabaseMock.addTrackToPlaylist(track, playlistId)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        DatabaseMock.toggleFavorite(track)
    }
    override suspend fun deleteTrackFromPlaylist(track: Track) {
        DatabaseMock.removeTrackFromPlaylist(track.id, 0L)
    }

    override suspend fun deleteTracksByPlaylistId(id: Long) {
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        val isFav = DatabaseMock.isFavorite(track.id)
        emit(track.copy(favorite = isFav))
    }
}