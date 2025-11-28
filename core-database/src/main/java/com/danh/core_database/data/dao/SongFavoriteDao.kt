package com.danh.core_database.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.danh.core_database.data.model.SongFavorite
import kotlinx.coroutines.flow.Flow
@Dao
interface SongFavoriteDao {
    @Query("select * from songfavorite")
    fun getAllSongFavorite(): Flow<List<SongFavorite>>
    @Insert
    suspend fun insertSong(song: SongFavorite)
    @Delete
    suspend fun deleteSong(song: SongFavorite)
}