package com.danh.core_network.model.song

import com.google.gson.annotations.SerializedName


data class SongList(@SerializedName("songs") val songs: List<Song>)
