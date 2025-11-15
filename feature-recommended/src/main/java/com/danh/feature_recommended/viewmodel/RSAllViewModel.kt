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

class RSAllViewModel(private val songRepository: SongRepository = SongRepositoryImpl()) :
    ViewModel() {
    private val _songList = MutableLiveData<List<Song>?>()
    var songList: LiveData<List<Song>?> = _songList
    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> = _error
    fun getAllSong() {
        viewModelScope.launch {
            val result = songRepository.getAllSongs()
            if (result is Result.Success) {
                _songList.value = result.data.songs
                _error.value = null
            } else if (result is Result.Error) {
                _songList.value = null
                _error.value = result.exception
            }
        }
    }
}