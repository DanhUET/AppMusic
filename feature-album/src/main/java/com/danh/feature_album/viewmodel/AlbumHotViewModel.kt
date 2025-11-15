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

class AlbumHotViewModel(private val albumRepository: AlbumRepository = AlbumRepositoryImpl()) :
    ViewModel() {
    private val _albumHot = MutableLiveData<List<Album>?>()
    var album:LiveData<List<Album>?> =_albumHot
    fun getAlbumHot() {
        viewModelScope.launch {
            val result=albumRepository.getAlbumHot()
            if(result is Result.Success){
                _albumHot.postValue(result.data.albums)
            }else if(result is Result.Error){
                _albumHot.postValue(null)
            }
        }
    }
}