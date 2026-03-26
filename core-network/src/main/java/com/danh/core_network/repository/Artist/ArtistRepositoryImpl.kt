package com.danh.core_network.repository.Artist

import com.danh.core_network.model.albums.AlbumList
import com.danh.core_network.model.artist.ArtistList
import com.danh.core_network.model.pagingRequest.PagingRequest
import com.danh.core_network.resource.RetrofitHelper
import com.danh.core_network.resource.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistRepositoryImpl: ArtistRepository {
    override suspend fun listArtist(): Result<ArtistList> {
        return withContext(Dispatchers.IO){
            val response= RetrofitHelper.artistApi.listArtist(PagingRequest(0,10))
            if(response.isSuccessful){
               Result.Success(response.body()!!)
            }else{
                Result.Error(Exception(response.message()))
            }

        }
    }

    override suspend fun allArtist(): Result<ArtistList> {
        return withContext(Dispatchers.IO){
            val response= RetrofitHelper.artistApi.allArtist()
            if(response.isSuccessful){
                Result.Success(response.body()!!)
            }else{
                Result.Error(Exception(response.message()))
            }
        }

    }
}