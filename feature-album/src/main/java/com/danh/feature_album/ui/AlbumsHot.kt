package com.danh.feature_album.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.danh.feature_album.R
import com.danh.feature_album.adapter.AlbumHotAdapter
import com.danh.feature_album.databinding.FragmentAlbumsHotBinding
import com.danh.feature_album.viewmodel.AlbumHotViewModel

class AlbumsHot : Fragment() {
    private lateinit var adapter: AlbumHotAdapter
    private lateinit var viewmodel: AlbumHotViewModel
    private lateinit var binding: FragmentAlbumsHotBinding

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
    }

    private fun setUpViews() {
        adapter= AlbumHotAdapter(mutableListOf())
        binding.rcyAlbumHot.adapter=adapter
        binding.rcyAlbumHot.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        viewmodel= AlbumHotViewModel()
        viewmodel.album.observe(viewLifecycleOwner){
            if(it!=null){
                adapter.updateAlbumHot(it)
            }
        }
        viewmodel.getAlbumHot()
    }

}