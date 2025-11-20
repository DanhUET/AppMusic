package com.danh.feature_player
import android.content.ComponentName
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.bumptech.glide.Glide
import com.danh.feature_player.databinding.FragmentMiniPlayerBinding
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class MiniPlayer : Fragment() {
    private var controller: MediaController? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private lateinit var binding: FragmentMiniPlayerBinding

    @UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionToken = SessionToken(
            requireContext(),
            ComponentName(requireContext(), SongService::class.java)
        )

        controllerFuture = MediaController.Builder(requireContext(), sessionToken)
            .buildAsync()

        controllerFuture?.addListener({
            controller = controllerFuture?.get()
            val c = controller ?: return@addListener
            updateMiniPlayerFromMetadata(c.mediaMetadata, c.isPlaying)
            // 🟢 Lắng nghe khi đổi bài / đổi trạng thái play-pause
            c.addListener(object : Player.Listener {
                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    updateMiniPlayerFromMetadata(mediaMetadata, c.isPlaying)
                }
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    updateMiniPlayerFromMetadata(c.mediaMetadata, isPlaying)
                }
            })
            // Nút Next
            binding.btnNext.setOnClickListener {
                controller?.seekToNextMediaItem()
            }

            // Nút Previous
            binding.btnFavorite.setOnClickListener {
                controller?.seekToPreviousMediaItem()
            }

            // Play/Pause
            binding.btnPlayOrPause.setOnClickListener {
                controller?.let { c ->
                    if (c.isPlaying) c.pause() else c.play()
                }
            }

        }, MoreExecutors.directExecutor())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMiniPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun updateMiniPlayerFromMetadata(
        mediaMetadata: MediaMetadata,
        isPlaying: Boolean
    ) {
        val title = mediaMetadata.title ?: ""
        val artist = mediaMetadata.artist ?: ""
        val artUri = mediaMetadata.artworkUri

        binding.tvItemTitle.text = title.toString()
        binding.tvItemArtist.text = artist.toString()

        // Ảnh cover
        if (artUri != null) {
            Glide.with(this)
                .load(artUri)
                .placeholder(R.drawable.ic_blur) // ảnh mặc định
                .centerCrop()
                .into(binding.image)
        } else {
            binding.image.setImageResource(R.drawable.ic_blur)
        }

        // Icon play/pause
        binding.btnPlayOrPause.setImageResource(
            if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        )
    }
    override fun onStop() {
        controllerFuture?.let { MediaController.releaseFuture(it) }
        controller = null
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            NowPlayer().show(requireActivity().supportFragmentManager, "MyDialog")
        }
    }
}