package com.example.playlistmaker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.data.database.dao.TracksDao
import com.example.playlistmaker.data.database.entity.TrackEntity

@Database(
    entities = [TrackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tracksDao(): TracksDao
}