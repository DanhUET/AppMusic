package com.danh.feature_player

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.danh.core_network.model.song.Song

class SongService : Service() {
    private lateinit var player: ExoPlayer

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
    }
    companion object {
        fun startPlay(
            context: Context,
            song: Song
        ) {
            val intent = Intent(context, SongService::class.java).apply {
                putExtra("source",song.source)
                putExtra("title",song.title)
                putExtra("image",song.image)
                putExtra("artist",song.artist)
            }
            context.startService(intent)
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val title= intent?.getStringExtra("title")
        val image=intent?.getStringExtra("image")
        val source= intent?.getStringExtra("source")
        val artist=intent?.getStringExtra("artist")
        playIndex(title,artist,source,image)
        return START_STICKY
    }

    private fun playIndex(title:String?,artist:String?,source:String?,image:String?) {
        val item = MediaItem.Builder()
            .setUri(source)
            .setMediaMetadata(
                androidx.media3.common.MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(artist)
                    .build()
            )
            .build()

        player.apply {
            setMediaItem(item)
            prepare()
            play()
        }
    }



    override fun onBind(intent: Intent?): IBinder? = null


}