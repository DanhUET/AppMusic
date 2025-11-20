package com.danh.feature_album.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.danh.core_navigation.home.AlbumsNavigation
import com.danh.core_navigation.home.RecommendedNavigation
import com.danh.core_network.model.albums.Album
import com.danh.feature_album.R
import com.danh.feature_album.adapter.AlbumHotAdapter
import com.danh.feature_album.databinding.FragmentAlbumsHotBinding
import com.danh.feature_album.viewmodel.AlbumHotViewModel

class AlbumsHot : Fragment() {
    private lateinit var adapter: AlbumHotAdapter
    private lateinit var viewmodel: AlbumHotViewModel
    private lateinit var binding: FragmentAlbumsHotBinding
    private val navigator: AlbumsNavigation by lazy {
        requireActivity() as AlbumsNavigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumsHotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        binding.btnMoreAlbum.setOnClickListener {
            navigator.openMoreAlbums(this)
        }
    }

    private fun setUpViews() {
        adapter= AlbumHotAdapter(mutableListOf(),object: AlbumHotAdapter.OnClickItem{
            override fun getDetailAlbum(album: Album) {
                navigator.openDetailAlbum(this@AlbumsHot,album.id)
            }

        } )
        binding.rcyAlbumHot.adapter=adapter
        binding.rcyAlbumHot.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        viewmodel= AlbumHotViewModel()
        viewmodel.album.observe(viewLifecycleOwner){
            if(it==null){
               binding.progressAlbum.visibility=View.VISIBLE
            }else{
                binding.progressAlbum.visibility= View.GONE
                adapter.updateAlbumHot(it)
            }
        }
        binding.progressAlbum.visibility=View.VISIBLE
        viewmodel.getAlbumHot()
    }
}