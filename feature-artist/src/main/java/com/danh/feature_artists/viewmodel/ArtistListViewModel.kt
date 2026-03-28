package com.danh.feature_artists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danh.core_network.model.artist.Artist
import com.danh.core_network.repository.Artist.ArtistRepository
import com.danh.core_network.repository.Artist.ArtistRepositoryImpl
import com.danh.core_network.resource.Result
import kotlinx.coroutines.launch

class ArtistListViewModel(private val artistRepository: ArtistRepository = ArtistRepositoryImpl()): ViewModel() {
    private val _artistList: MutableLiveData<List<Artist>?> = MutableLiveData()
    var artistList: LiveData<List<Artist>?> = _artistList
    private val _artistInfo: MutableLiveData<Artist?> = MutableLiveData()
    var artistInfo: LiveData<Artist?> = _artistInfo
    fun listArtist(){
        viewModelScope.launch {
            val result=artistRepository.listArtist()
            if(result is Result.Success){
                _artistList.postValue(result.data.artists)
            }else{
               _artistList.postValue(null)
            }
        }
    }
    fun allArtist(){
        viewModelScope.launch {
            val result=artistRepository.allArtist()
            if(result is Result.Success){
                _artistList.postValue(result.data.artists)
            }else{
                _artistList.postValue(null)
            }
        }
    }
    fun informationArtist(id:String){
        viewModelScope.launch {
            val result=artistRepository.informationArtist(id)
            if(result is Result.Success){
                _artistInfo.postValue(result.data)
            }else{
                _artistInfo.postValue(null)
            }
        }
    }
}