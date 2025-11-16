package com.danh.feature_album.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danh.core_network.model.song.Song
import com.danh.core_network.repository.albums.AlbumRepository
import com.danh.core_network.repository.albums.AlbumRepositoryImpl
import com.danh.core_network.resource.Result
import kotlinx.coroutines.launch

class AlbumDetailViewModel(val id:String,private val albumRepository: AlbumRepository= AlbumRepositoryImpl()): ViewModel() {
    private val _detailAlbumList= MutableLiveData<List<Song>?>()
    var detailAlbumList: LiveData<List<Song>?> =_detailAlbumList
    fun getDetailAlbum(){
        viewModelScope.launch {
            val result=albumRepository.getDetailAlbum(id)
            if(result is Result.Success){
                _detailAlbumList.postValue(result.data.songs)
            }else if(result is Result.Error){
                _detailAlbumList.value=null
            }
        }
    }
}