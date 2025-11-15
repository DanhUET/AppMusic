package com.danh.feature_recommended.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danh.core_network.model.song.Song
import com.danh.core_network.repository.Song.SongRepository
import com.danh.core_network.repository.Song.SongRepositoryImpl
import com.danh.core_network.resource.Result
import kotlinx.coroutines.launch

class RSViewModel(private val songRepository: SongRepository= SongRepositoryImpl() ): ViewModel() {
    private val _listSong = MutableLiveData<List<Song>?>()
    val listSong: LiveData<List<Song>?> = _listSong
    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> = _error
    fun getRecommendedSong(){
        viewModelScope.launch {
            val result=songRepository.getRecommendedSong()
            if(result is Result.Success){
                _listSong.value=result.data.songs
                _error.value=null
            }else if(result is Result.Error){
                _listSong.value=null
                _error.value=result.exception
            }
        }
    }
}