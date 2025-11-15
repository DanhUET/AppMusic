package com.danh.core_network.repository.Song

import com.danh.core_network.model.song.SongList
import com.danh.core_network.resource.Result

interface SongRepository {
    suspend fun getAllSongs(): Result<SongList>
    suspend fun getRecommendedSong(): Result<SongList>
}
