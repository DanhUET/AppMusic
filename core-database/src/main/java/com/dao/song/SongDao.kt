package com.dao.song

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.entity.song.SongFavorite

@Dao
interface SongDao {
    @Query("select * from SongFavorite")
    fun getAll(): List<SongFavorite>

    @Query("select * from SongFavorite where id = :id")
    fun findById(id: String): SongFavorite?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertById(song: SongFavorite)
    @Delete
    suspend fun deleteById(song: SongFavorite)
}