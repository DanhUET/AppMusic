package com.danh.core_network.model.media

import com.danh.core_network.model.song.Song

fun Song.toMediaItemData(): MediaItemData {
    return MediaItemData(
        mediaId = this.id,
        title = this.title,
        subtitle = this.artist,
        albumArtUri = this.image,
        source = this.source
    )
}