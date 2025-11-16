package com.danh.core_network.resource

import com.danh.core_network.model.albums.AlbumList
import com.danh.core_network.model.pagingRequest.PagingRequest
import com.danh.core_network.model.song.SongList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {
    @POST("services.php/albums")
    suspend fun getAlbumsHot(@Body request: PagingRequest): Response<AlbumList>

    @GET("services.php?queryType=allAlbums")
    suspend fun getAllAlbums(): Response<AlbumList>

    @POST("services.php?queryType=albumWithSongs")
    suspend fun getDetailAlbum(
        @Query("albumId") id: String
    ): Response<SongList>
}