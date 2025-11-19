package com.danh.core_network.repository.Song

import com.danh.core_network.model.pagingRequest.PagingRequest
import com.danh.core_network.model.song.SongList
import com.danh.core_network.resource.Result
import com.danh.core_network.resource.RetrofitHelper
import com.danh.core_network.resource.SongApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepositoryImpl : SongRepository {
    override suspend fun getAllSongs(): Result<SongList> {
        return withContext(Dispatchers.IO) {
            val response = RetrofitHelper.songApi.getAllSongs()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception(response.message()))
            }
        }
    }

    override suspend fun getRecommendedSong(): Result<SongList> {
        return withContext(Dispatchers.IO) {
            val request = PagingRequest(10, 20)
            val response = RetrofitHelper.songApi.getRecommendedSongs(request)

            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception(response.message()))
            }
        }
    }
}