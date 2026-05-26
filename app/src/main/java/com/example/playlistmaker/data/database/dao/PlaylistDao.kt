package com.example.playlistmaker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.playlistmaker.data.database.entity.PlaylistEntity
import com.example.playlistmaker.data.database.entity.PlaylistWithTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylistById(id: Long)

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylistsWithTracks(): Flow<List<PlaylistWithTracks>>

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :id")
    fun getPlaylistWithTracksById(id: Long): Flow<PlaylistWithTracks?>

}