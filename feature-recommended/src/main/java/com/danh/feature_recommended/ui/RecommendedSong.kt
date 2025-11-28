package com.danh.feature_recommended.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danh.core_navigation.home.RecommendedNavigation
import com.danh.core_network.model.song.Song
import com.danh.core_ui.ui.SongBlurAdapter
import com.danh.feature_player.MiniPlayerHost
import com.danh.feature_player.SongService
import com.danh.feature_recommended.adapter.RSAdapter
import com.danh.feature_recommended.databinding.FragmentRecommendedSongBinding
import com.danh.feature_recommended.viewmodel.RSViewModel
import com.google.android.material.snackbar.Snackbar

class RecommendedSong( ) : Fragment() {
    private var controller: MediaController?=null
    private val navigator: RecommendedNavigation by lazy {
        requireActivity() as RecommendedNavigation
    }
    private lateinit var binding: FragmentRecommendedSongBinding
    private lateinit var recommendedSongAdapter: RSAdapter
    private lateinit var recommendedViewModel : RSViewModel
    private lateinit var adapterBlurSong: SongBlurAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRecommendedSongBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

        binding.btnAllSong.setOnClickListener {
            navigator.openMoreRecommended(this)
        }

    }

    private fun setUpViews() {
        recommendedSongAdapter= RSAdapter(mutableListOf(),object : RSAdapter.OnClickItem{
            @SuppressLint("UnsafeOptInUsageError")
            override fun playMusic(
                songList: List<Song>,
                position: Int
            ) {
                (requireActivity() as MiniPlayerHost).showMiniPlayer()
                SongService.startPlay(requireContext(),songList,position)
            }

        })
        adapterBlurSong= SongBlurAdapter()
        binding.rcyRS.adapter=recommendedSongAdapter
        binding.rcyRS.layoutManager = LinearLayoutManager(requireContext())
        binding.rcyBlur.adapter=adapterBlurSong
        binding.rcyBlur.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        recommendedViewModel= RSViewModel()
        recommendedViewModel.listSong.observe(viewLifecycleOwner){
            if(it==null){
                binding.shimmerLayout.visibility= View.VISIBLE
            }else{
                binding.shimmerLayout.visibility=View.GONE
                recommendedSongAdapter.updateSongs(it)
            }
        }
        recommendedViewModel.getRecommendedSong()
    }

}