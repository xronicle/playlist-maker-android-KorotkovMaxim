package com.example.playlistmaker.creator

import com.example.playlistmaker.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackId = 1L,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158000, // 2:38
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 2L,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000, // 4:43
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 3L,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000, // 5:12
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 4L,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000,
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 5L,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000,
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 6L,
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000,
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 7L,
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000,
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 8L,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000,
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 9L,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000,
            previewUrl = "",
            artworkUrl100 = ""
        ),
        TrackDto(
            trackId = 10L,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000,
            previewUrl = "",
            artworkUrl100 = ""
        )
    )

    fun search(request: String): List<TrackDto> {
        val result = listTracks.filter {
            val trackName = it.trackName.lowercase()
            val artistName = it.artistName.lowercase()
            val req = request.lowercase()
            trackName.contains(req) || artistName.contains(req)
        }
        return result
    }
}