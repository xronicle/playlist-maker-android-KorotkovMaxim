package com.example.playlistmaker.data.database

import com.example.playlistmaker.data.database.entity.PlaylistWithTracks
import com.example.playlistmaker.data.database.entity.TrackEntity
import com.example.playlistmaker.domain.models.Playlist
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
        )
    }

    fun map(track: Track): TrackEntity {
        return TrackEntity(
            id = track.id,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTime = track.trackTime,
            image = track.image,
            favorite = track.favorite
        )
    }
}

object PlaylistDbConverter {
    fun map(entityWithTracks: PlaylistWithTracks): Playlist {
        return Playlist(
            id = entityWithTracks.playlist.id,
            name = entityWithTracks.playlist.name,
            description = entityWithTracks.playlist.description,
            imageUri = entityWithTracks.playlist.imageUri,
            tracks = entityWithTracks.tracks.map { TrackDbConverter.map(it) }
        )
    }
}