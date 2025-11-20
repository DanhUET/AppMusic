package com.danh.feature_player

import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.bumptech.glide.Glide
import com.danh.feature_player.databinding.FragmentNowPlayerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class NowPlayer : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNowPlayerBinding
    private var controller: MediaController? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private var isUserSeeking = false

    private val updateProgressAction = object : Runnable {
        override fun run() {
            val c = controller
            if (c != null && !isUserSeeking && c.playbackState != Player.STATE_IDLE) {
                val duration = c.duration
                val position = c.currentPosition

                if (duration > 0 && duration.toInt() != Player.STATE_ENDED) {
                    binding.seekBar.max = duration.toInt()
                    binding.seekBar.progress = position.toInt()
                }
            }
            // lặp lại sau 500ms
            handler.postDelayed(this, 500)
        }
    }

    @UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionToken = SessionToken(
            requireContext(), ComponentName(
                requireContext(),
                SongService::class.java
            )
        )
        controllerFuture = MediaController.Builder(requireContext(), sessionToken).buildAsync()
        controllerFuture?.addListener({
            controller = controllerFuture?.get()
            val c = controller ?: return@addListener
            updateNowPlayFromMetadata(c.mediaMetadata, c.isPlaying)
            c.addListener(object : Player.Listener {
                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    updateNowPlayFromMetadata(mediaMetadata, c.isPlaying)
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    updateNowPlayFromMetadata(c.mediaMetadata, isPlaying)
                }
            })
            binding.btnSkip.setOnClickListener {
                controller?.seekToNextMediaItem()
            }
            binding.btnPrevours.setOnClickListener {
                controller?.seekToPreviousMediaItem()
            }
            binding.btnPlayPause.setOnClickListener {
                controller?.let { c ->
                    if (c.isPlaying) c.pause() else c.play()
                }
            }
            // === SeekBar điều khiển trình phát ===
            setupSeekBar(c)

            // Bắt đầu auto cập nhật progress
            handler.post(updateProgressAction)

        }, MoreExecutors.directExecutor())

    }

    private fun updateNowPlayFromMetadata(
        mediaMetadata: MediaMetadata,
        isPlaying: Boolean
    ) {
        val title = mediaMetadata.title ?: ""
        val artist = mediaMetadata.artist ?: ""
        val artUri = mediaMetadata.artworkUri

        binding.tvTitleNowplay.text = title.toString()
        binding.tvArtistNowplay.text = artist.toString()
        if (artUri != null) {
            Glide.with(this)
                .load(artUri)
                .placeholder(com.danh.core_ui.R.drawable.ic_error_music) // ảnh mặc định
                .centerCrop()
                .into(binding.imageNowPlay)
        } else {
            binding.imageNowPlay.setImageResource(R.drawable.ic_blur)
        }

        // Icon play/pause
        binding.btnPlayPause.setImageResource(
            if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        )
    }

    override fun getTheme(): Int = R.style.NowPlayerBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let { sheet ->
            sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

            val behavior = BottomSheetBehavior.from(sheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true        // không hiện dạng “nửa màn”
        }
    }

    override fun onStop() {
        controllerFuture?.let { MediaController.releaseFuture(it) }
        controller = null
        handler.removeCallbacks(updateProgressAction)  // dừng cập nhật SeekBar
        super.onStop()
    }
    private fun setupSeekBar(c: MediaController) {
        // Set max lần đầu nếu có duration
        val duration = c.duration
        if (duration > 0 && duration.toInt() != Player.STATE_ENDED) {
            binding.seekBar.max = duration.toInt()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {
                isUserSeeking = false
                val newPos = seekBar?.progress?.toLong() ?: 0L
                controller?.seekTo(newPos)
            }

            override fun onProgressChanged(
                seekBar: android.widget.SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                // Nếu muốn, có thể cập nhật TextView thời gian ở đây khi fromUser = true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            dismiss()
        }
    }
}