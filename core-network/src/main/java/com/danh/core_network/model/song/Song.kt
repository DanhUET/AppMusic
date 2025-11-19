package com.danh.core_network.model.song
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName
@Parcelize
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val source: String,
    val image: String,
    val duration:String,
    val favorite:String,
    val counter:String,
    val replay:String,
    @SerializedName("track_number") val trackNumber:String?=null,
) : Parcelable