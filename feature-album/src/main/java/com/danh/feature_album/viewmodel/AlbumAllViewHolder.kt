package com.danh.feature_album.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danh.core_network.model.albums.Album
import com.danh.core_network.repository.albums.AlbumRepository
import com.danh.core_network.repository.albums.AlbumRepositoryImpl
import com.danh.core_network.resource.Result
import kotlinx.coroutines.launch

class AlbumAllViewHolder(private val albumRepository: AlbumRepository = AlbumRepositoryImpl()) :
    ViewModel() {
    private val _albumAllList = MutableLiveData<List<Album>?>()
    var albumAllList: LiveData<List<Album>?> = _albumAllList
    fun getAllAlbum() {
        viewModelScope.launch {
            val result = albumRepository.getAllAlbums()
            if (result is Result.Success) {
                _albumAllList.postValue(result.data.albums)
            } else if (result is Result.Error) {
                _albumAllList.postValue(null)
            }
        }
    }

}