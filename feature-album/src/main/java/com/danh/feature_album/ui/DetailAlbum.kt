package com.danh.feature_album.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.danh.core_network.model.song.Song
import com.danh.feature_album.R
import com.danh.feature_album.adapter.AlbumDetailAdapter
import com.danh.feature_album.databinding.FragmentDetailAlbumBinding
import com.danh.feature_album.viewmodel.AlbumDetailViewModel
import com.danh.feature_player.MiniPlayerHost
import com.danh.feature_player.SongService

class DetailAlbum : Fragment() {
    private lateinit var binding: FragmentDetailAlbumBinding
    private lateinit var adapter: AlbumDetailAdapter
    private lateinit var viewmodel: AlbumDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val navController = findNavController()
        // 2. Thiết lập toolbar đi với NavController
        binding.toolbar.setupWithNavController(navController)
        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayShowTitleEnabled(false)
        setUpViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {
        adapter = AlbumDetailAdapter(mutableListOf(),object : AlbumDetailAdapter.OnClickItem{
            @SuppressLint("UnsafeOptInUsageError")
            override fun clickItem(
                songList: List<Song>,
                position: Int
            ) {
                (requireActivity() as MiniPlayerHost).showMiniPlayer()
                SongService.startPlay(requireContext(),songList,position)
            }

        })

        val messageId = arguments?.getString("idAlbum", "0")
        binding.rcyDetailAlbum.adapter = adapter
        binding.rcyDetailAlbum.layoutManager = LinearLayoutManager(requireContext())
        viewmodel = AlbumDetailViewModel(messageId!!)
        viewmodel.detailAlbumList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.progress.visibility = View.GONE
                adapter.updateSongs(it)
                val firstSong = it.first()
                val songlist = it
                Glide.with(binding.root).load(firstSong.image).into(binding.imageAlbum)
                binding.title.text = firstSong.title
                binding.number.text = "Number of songs: ${songlist.size}"
            } else {
                binding.progress.visibility = View.VISIBLE
            }
        }
        binding.progress.visibility = View.VISIBLE
        viewmodel.getDetailAlbum()
    }
}