package com.danh.feature_album.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danh.core_navigation.home.AlbumsNavigation
import com.danh.core_network.model.albums.Album
import com.danh.feature_album.R
import com.danh.feature_album.adapter.AlbumAllAdapter
import com.danh.feature_album.databinding.FragmentListAlbumsBinding
import com.danh.feature_album.viewmodel.AlbumAllViewHolder

class ListAlbums : Fragment() {
    private lateinit var binding: FragmentListAlbumsBinding
    private lateinit var adapter: AlbumAllAdapter
    private lateinit var viewmodel: AlbumAllViewHolder
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
        binding= FragmentListAlbumsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun setUpViews() {
        adapter= AlbumAllAdapter(mutableListOf(),object : AlbumAllAdapter.OnItemClick{
            override fun clickItem(album: Album) {
                navigator.openDetailAlbum(this@ListAlbums,album.id)
            }

        })
        viewmodel= AlbumAllViewHolder()
        binding.rcyAllAlbum.adapter=adapter
        binding.rcyAllAlbum.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )
        viewmodel.albumAllList.observe(viewLifecycleOwner){
            if(it!=null){
                adapter.updateAllAlbum(it)
            }
        }
        viewmodel.getAllAlbum()
    }

}