package com.danh.core_network.model.albums

import com.google.gson.annotations.SerializedName

data class Album(
    val id: String,
    val name: String,
    @SerializedName("artwork") val image: String,
    val size: String
)