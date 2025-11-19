package com.danh.core_network.model.media

import androidx.recyclerview.widget.DiffUtil

data class MediaItemData(
    val mediaId: String,
    val title: String,
    val subtitle: String,
    val albumArtUri: String,
    val source: String
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MediaItemData>() {
            override fun areContentsTheSame(
                oldItem: MediaItemData,
                newItem: MediaItemData
            ): Boolean = oldItem.mediaId == newItem.mediaId

            override fun areItemsTheSame(oldItem: MediaItemData, newItem: MediaItemData): Boolean =
                oldItem.mediaId == newItem.mediaId
        }
    }
}