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
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!)
            .setId("DanhMusicSession")
            .build()
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
                        MediaItem.Builder()
                            .setUri(song.source)
                            .setMediaId(song.id)
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setTitle(song.title)
                                    .setArtist(song.artist)
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

                    // Đảm bảo đã startForeground với notification
//                    startForegroundWithNotification()
                }
            }
            ACTION_NEXT -> {
                player?.seekToNextMediaItem()
    //            startForegroundWithNotification() // cập nhật title/ảnh bài mới
            }

            ACTION_PREV -> {
                player?.seekToPreviousMediaItem()
    //            startForegroundWithNotification()
            }

            ACTION_PLAY_PAUSE -> {
                player?.let { p ->
                    if (p.isPlaying) p.pause() else p.play()
    //                startForegroundWithNotification()
                }
            }
        }

        return START_STICKY
    }

    // 5. Tạo notification dạng MediaStyle + đưa service lên foreground
    @UnstableApi
    private fun startForegroundWithNotification() {
        val session = mediaSession ?: return
        val p = session.player

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music playback",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
        val preIntent=Intent(this, SongService::class.java).apply { action=ACTION_PREV }
        val nextIntent=Intent(this, SongService::class.java).apply { action=ACTION_NEXT }
        val playPauseIntent = Intent(this, SongService::class.java).apply { action = ACTION_PLAY_PAUSE }

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        val prevPendingIntent = PendingIntent.getService(this, 0, preIntent, flags)
        val nextPendingIntent = PendingIntent.getService(this, 1, nextIntent, flags)
        val playPausePendingIntent = PendingIntent.getService(this, 2, playPauseIntent, flags)

        val style = MediaStyleNotificationHelper.MediaStyle(session)

        val title = p.mediaMetadata.title ?: "Đang phát nhạc"
        val artist = p.mediaMetadata.artist ?: ""

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(com.danh.core_ui.R.drawable.ic_error_music) // icon của bạn
            .setContentTitle(title)
            .setContentText(artist)
            .setStyle(style)
            .setContentIntent(session.sessionActivity) // mở UI khi bấm notification
            .setOngoing(true)
            .addAction(R.drawable.ic_skip_previous, "Prev", prevPendingIntent)
            .addAction(
                if (p.isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                if (p.isPlaying) "Pause" else "Play",
                playPausePendingIntent
            )
            .addAction(R.drawable.ic_skip_next, "Next", nextPendingIntent)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        mediaSession?.release()
        mediaSession = null
        player?.release()
        player = null
        super.onDestroy()
    }

    companion object {
        private const val CHANNEL_ID = "music_channel"
        private const val NOTIFICATION_ID = 1
        private const val ACTION_START = "action_start"
        private const val ACTION_NEXT = "action_next"
        private const val ACTION_PREV = "action_prev"
        private const val ACTION_PLAY_PAUSE = "action_play_pause"
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