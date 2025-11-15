package com.danh.core_network.repository.albums

import com.danh.core_network.model.albums.AlbumList
import com.danh.core_network.model.pagingRequest.PagingRequest
import com.danh.core_network.resource.AlbumApi
import com.danh.core_network.resource.Result
import com.danh.core_network.resource.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepositoryImpl : AlbumRepository {
    override suspend fun getAlbumHot(): Result<AlbumList> {
        val request = PagingRequest(10,20)
        return withContext(Dispatchers.IO) {
            val result = RetrofitHelper.getInstance().create(AlbumApi::class.java).getAlumsHot(request)
            if (result.isSuccessful) {
                Result.Success(result.body()!!)
            } else {
                Result.Error(Exception(result.message()))
            }
        }
    }
}