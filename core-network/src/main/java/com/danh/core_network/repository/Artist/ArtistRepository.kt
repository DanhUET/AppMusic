package com.danh.core_network.repository.Artist

import com.danh.core_network.model.artist.ArtistList
import com.danh.core_network.resource.Result
interface ArtistRepository {
    suspend fun listArtist(): Result<ArtistList>
    suspend fun allArtist(): Result<ArtistList>
}