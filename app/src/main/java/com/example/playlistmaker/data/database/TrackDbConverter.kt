package com.example.playlistmaker.data.database

import com.example.playlistmaker.data.database.entity.TrackEntity
import com.example.playlistmaker.domain.models.Track

object TrackDbConverter {
    fun map(entity: TrackEntity): Track {
        return Track(
            id = entity.id,
            trackName = entity.trackName,
            artistName = entity.artistName,
            trackTime = entity.trackTime,
            image = entity.image,
            favorite = entity.favorite,
            playlistId = entity.playlistId
        )
    }

    fun map(track: Track): TrackEntity {
        return TrackEntity(
            id = track.id,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTime = track.trackTime,
            image = track.image,
            favorite = track.favorite,
            playlistId = track.playlistId
        )
    }
}