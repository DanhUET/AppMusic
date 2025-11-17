package com.danh.feature_player

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper
import com.danh.core_network.model.song.Song
import java.util.ArrayList

class SongService : Service() {
    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        player = ExoPlayer.Builder(this).build()
        mediaSession= MediaSession.Builder(this,player).build()
    }

    companion object {
        fun startPlay(
            context: Context,
            songList: List<Song>,
            position:Int
        ) {
            val intent = Intent(context, SongService::class.java).apply {
                putParcelableArrayListExtra("songs", ArrayList(songList))
                putExtra("position",position)
            }
            context.startService(intent)
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songs = intent?.getParcelableArrayListExtra<Song>("songs") ?: arrayListOf()
        val position = intent?.getIntExtra("position", 0) ?: 0
        playIndex(songs, position)
        return START_STICKY
    }

    private fun playIndex(songs:List<Song>,position: Int) {
        val mediaItems=songs.map {song->
            MediaItem.Builder().setUri(song.source) .setMediaMetadata(
                androidx.media3.common.MediaMetadata.Builder()
                    .setTitle(songs[position].title)
                    .setArtist(songs[position].artist)
                    .setArtworkUri(song.image.toUri())
                    .build()
            )
                .build()
        }
        createNotification()
        player.apply {
            clearMediaItems()
            setMediaItems(mediaItems,position,0L)
            prepare()
            play()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNEL_NAME"
            val descriptionText = "This is a channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("CHANEL_ID", name, importance).apply {
                description = descriptionText
                importance
            }
            val notificationManager: NotificationManager =
                getSystemService(
                    baseContext,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    @OptIn(UnstableApi::class)
    private fun createNotification() {
        val notification = NotificationCompat.Builder(this, "CHANEL_ID")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(com.danh.core_ui.R.drawable.ic_error_music)
            .addAction(R.drawable.ic_skip_next, "Previous", null) // #0
            .addAction(R.drawable.ic_pause, "Pause", null) // #1
            .addAction(R.drawable.ic_skip_next, "Next", null) // #2
            .setStyle( MediaStyleNotificationHelper.MediaStyle(mediaSession)
                .setShowActionsInCompactView(0,1,2))
            .build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null

}