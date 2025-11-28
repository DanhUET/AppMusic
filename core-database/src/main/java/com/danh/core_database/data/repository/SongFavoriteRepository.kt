package com.danh.core_database.data.repository

import com.danh.core_database.data.dao.SongFavoriteDao
import com.danh.core_database.data.model.SongFavorite

class SongFavoriteRepository(private val songFavoriteDao: SongFavoriteDao) {
    val SongFavorites=songFavoriteDao.getAllSongFavorite()
    suspend fun insertSongFavorite(song: SongFavorite) =songFavoriteDao.insertSong(song)
    suspend fun deleteSongFavorite(song: SongFavorite)=songFavoriteDao.deleteSong(song)
}