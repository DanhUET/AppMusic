package com.entity.song

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongFavorite(
    @PrimaryKey val id: String,
    val title: String,
    val artist: String,
    val source: String,
)
