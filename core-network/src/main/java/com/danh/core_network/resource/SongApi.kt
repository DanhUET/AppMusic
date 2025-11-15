package com.danh.core_network.resource

import com.danh.core_network.model.pagingRequest.PagingRequest
import com.danh.core_network.model.song.SongList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SongApi {
    @GET("services.php?queryType=allSongs")
    suspend fun getAllSongs(): Response<SongList>
    @POST("services.php/songs")
    suspend fun getRecommendedSongs(@Body request: PagingRequest): Response<SongList>
}

