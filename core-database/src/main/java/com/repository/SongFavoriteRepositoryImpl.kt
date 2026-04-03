package com.repository

import com.dao.song.SongDao
import com.entity.song.SongFavorite

class SongFavoriteRepositoryImpl(private val songDao: SongDao): SongFavoriteRepository {
    override fun getAllSongs() {
        songDao.getAll()
    }

    override suspend fun toggleFavorite(song: SongFavorite) {
       if(songDao.findById(song.id)!=null){
           songDao.deleteById(song)
       }else{
           songDao.insertById(song)
       }
    }
}