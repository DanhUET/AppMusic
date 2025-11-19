package com.danh.core_network.resource

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val BASE_URL = "https://thantrieu.com/services/"
    private val gson by lazy {
        GsonBuilder().create()
    }
    private val retrofit by lazy{
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
    val songApi: SongApi by lazy {
        retrofit.create(SongApi::class.java)
    }
    val albumApi: AlbumApi by lazy {
        retrofit.create(AlbumApi::class.java)
    }
}