package com.danh.core_network.resource.Artist

import com.danh.core_network.model.artist.ArtistList
import com.danh.core_network.model.pagingRequest.PagingRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ArtistApi {
    @POST("services.php/artists")
    suspend fun listArtist( @Body request: PagingRequest): Response<ArtistList>
    @GET("services.php?queryType=allArtists")
    suspend fun allArtist(): Response<ArtistList>
}