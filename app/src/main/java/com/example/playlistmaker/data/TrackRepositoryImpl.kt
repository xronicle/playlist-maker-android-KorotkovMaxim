package com.example.playlistmaker.data

import com.example.playlistmaker.data.dto.TrackSearchRequest
import com.example.playlistmaker.data.dto.TrackSearchResponse
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.delay

val listTracks = listOf(
    Track(
        trackName = "Владивосток 2000",
        artistName = "Мумий Троль",
        trackTime = "2:38"
    ),
    Track(
        trackName = "Группа крови",
        artistName = "Кино",
        trackTime = "4:43"
    ),
    Track(
        trackName = "Не смотри назад",
        artistName = "Ария",
        trackTime = "5:12"
    ),
    Track(
        trackName = "Звезда по имени Солнце",
        artistName = "Кино",
        trackTime = "3:45"
    ),
    Track(
        trackName = "Лондон",
        artistName = "Аквариум",
        trackTime = "4:32"
    ),
    Track(
        trackName = "На заре",
        artistName = "Альянс",
        trackTime = "3:50"
    ),
    Track(
        trackName = "Перемен",
        artistName = "Кино",
        trackTime = "4:56"
    ),
    Track(
        trackName = "Розовый фламинго",
        artistName = "Сплин",
        trackTime = "3:15"
    ),
    Track(
        trackName = "Танцевать",
        artistName = "Мельница",
        trackTime = "3:42"
    ),
    Track(
        trackName = "Чёрный бумер",
        artistName = "Серега",
        trackTime = "4:01"
    )
)
class TrackRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            (response as TrackSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + "%02d".format(seconds - minutes * 60)
                Track(it.trackName, it.trackName, trackTime)
            }
        } else {
            emptyList()
        }
    }
}