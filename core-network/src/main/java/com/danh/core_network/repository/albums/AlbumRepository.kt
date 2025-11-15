package com.danh.core_network.repository.albums

import com.danh.core_network.model.albums.AlbumList
import com.danh.core_network.resource.Result

interface AlbumRepository {
    suspend fun getAlbumHot() : Result<AlbumList>
}