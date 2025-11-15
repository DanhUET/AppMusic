package com.danh.feature_recommended

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danh.feature_recommended.adapter.RSAllAdapter
import com.danh.feature_recommended.databinding.FragmentRecommendedAllSongBinding
import com.danh.feature_recommended.viewmodel.RSAllViewModel

class RecommendedAllSong : Fragment() {
    private lateinit var binding: FragmentRecommendedAllSongBinding
    private lateinit var adapter: RSAllAdapter
    private lateinit var viewmodel: RSAllViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRecommendedAllSongBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarRecommendedSong)

        val navController = findNavController()
        // 2. Thiết lập toolbar đi với NavController
        binding.toolbarRecommendedSong.setupWithNavController(navController)
        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayShowTitleEnabled(false)
        setUpViews()
    }

    private fun setUpViews() {
        adapter= RSAllAdapter(mutableListOf())
        binding.rcyAllSong.adapter=adapter
        binding.rcyAllSong.layoutManager= LinearLayoutManager(requireContext())
        viewmodel= RSAllViewModel()
        viewmodel.songList.observe(viewLifecycleOwner){
            if(it!=null){
                adapter.updateAllSongs(it)
            }
        }
        viewmodel.getAllSong()
    }

}