package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.database.AppDatabase
import com.example.playlistmaker.data.database.TrackDbConverter
import com.example.playlistmaker.data.dto.TracksSearchRequest
import com.example.playlistmaker.data.dto.TracksSearchResponse
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Resource
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: AppDatabase
) : TracksRepository {

    private val tracksDao = database.tracksDao()

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
        tracksDao.insertTrack(TrackDbConverter.map(track.copy(playlistId = playlistId)))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        tracksDao.insertTrack(TrackDbConverter.map(track.copy(favorite = isFavorite)))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksDao.deleteTrack(TrackDbConverter.map(track))
    }

    override suspend fun deleteTracksByPlaylistId(id: Long) {
        tracksDao.deleteTracksByPlaylistId(id)
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return tracksDao.getTrackByNameAndArtist(track.trackName, track.artistName)
            .map { entity -> entity?.let { TrackDbConverter.map(it) } }
    }
}