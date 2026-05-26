package com.example.playlistmaker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.data.database.dao.PlaylistDao
import com.example.playlistmaker.data.database.dao.TracksDao
import com.example.playlistmaker.data.database.entity.PlaylistEntity
import com.example.playlistmaker.data.database.entity.PlaylistTrackCrossRef
import com.example.playlistmaker.data.database.entity.TrackEntity


@Database(
    entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tracksDao(): TracksDao
    abstract fun playlistDao(): PlaylistDao
}