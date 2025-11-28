package com.danh.feature_player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danh.core_database.data.model.SongFavorite
import com.danh.core_database.data.repository.SongFavoriteRepository
import com.danh.core_network.model.song.Song
import kotlinx.coroutines.launch

class ViewModelFavorite(private val songRepositoryFavorite: SongFavoriteRepository,private val songFavorite: SongFavorite): ViewModel() {
    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun toggleFavorite() {
       viewModelScope.launch {
           val current = _isFavorite.value ?: false
           if(!current){
               songRepositoryFavorite.insertSongFavorite(songFavorite)
               _isFavorite.value = true
           }else{
               _isFavorite.value = false
               songRepositoryFavorite.deleteSongFavorite(songFavorite)
           }
       }

    }
}