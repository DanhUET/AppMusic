package com.danh.feature_player

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.MediaStyleNotificationHelper
import com.danh.core_network.model.song.Song
import kotlinx.serialization.builtins.ArraySerializer

@UnstableApi
class SongService : MediaSessionService() {

    private var player: ExoPlayer? = null
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!)
            .setId("DanhMusicSession")
            .build()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Phát nhạc AppMusic",
                NotificationManager.IMPORTANCE_LOW
            )
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    // 3. Trả về session cho các controller (UI) kết nối
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    // 4. Nhận Intent khi startForegroundService (truyền URL bài hát/v.v.)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_START -> {

                val position = intent.getIntExtra("position", 0)
                val songList = intent.getParcelableArrayListExtra<Song>("songList") ?: arrayListOf()
                if (songList.isNotEmpty()) {
                    // Ví dụ: set playlist cho ExoPlayer
                    val mediaItems = songList.map { song ->
                        val bundle = Bundle().apply {
                            putParcelable("song", song)
                        }

                        MediaItem.Builder()
                            .setUri(song.source)
                            .setMediaId(song.id)
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setTitle(song.title)
                                    .setArtist(song.artist)
                                    .setExtras(bundle)
                                    .setArtworkUri(song.image.toUri())
                                    .build()
                            )
                            .build()
                    }

                    player?.apply {
                        stop()
                        clearMediaItems()
                        setMediaItems(mediaItems, position, 0L)
                        prepare()
                        playWhenReady = true
                        play()
                    }
                }
            }
        }

        return START_STICKY
    }
    override fun onDestroy() {
        mediaSession?.release()
        mediaSession = null
        player?.release()
        player = null
        super.onDestroy()
    }
    companion object {
        private const val ACTION_START = "action_start"
        private const val CHANNEL_ID = "song_playback"
        private const val NOTIFICATION_ID = 1

        fun startPlay(context: Context, songList: List<Song>, position: Int) {
            val intent = Intent(context, SongService::class.java).apply {
                action = ACTION_START
                putExtra("position", position)
                putParcelableArrayListExtra("songList", ArrayList(songList))
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }
}