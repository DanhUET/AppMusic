package com.danh.core_network.repository.albums

import com.danh.core_network.model.albums.AlbumList
import com.danh.core_network.model.song.SongList
import com.danh.core_network.resource.Result

interface AlbumRepository {
    suspend fun getAlbumHot() : Result<AlbumList>
    suspend fun getAllAlbums() :Result<AlbumList>
    suspend fun getDetailAlbum(id:String) : Result<SongList>
}