package com.danh.core_database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class SongFavorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val artist: String,
    val image: String,
    val source: String,
)