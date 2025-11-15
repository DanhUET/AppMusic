package com.danh.core_network.resource

import com.danh.core_network.model.albums.AlbumList
import com.danh.core_network.model.pagingRequest.PagingRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumApi {
    @POST("services.php/albums")
    suspend fun getAlumsHot(@Body request: PagingRequest): Response<AlbumList>
}