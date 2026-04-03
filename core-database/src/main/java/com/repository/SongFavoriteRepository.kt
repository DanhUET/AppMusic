package com.repository

import com.dao.song.SongDao
import com.entity.song.SongFavorite

interface SongFavoriteRepository {
    fun getAllSongs()
    suspend fun toggleFavorite(song: SongFavorite)
}