package com.danh.feature_artists.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danh.core_navigation.library.ArtistsNavigation
import com.danh.feature_artists.R
import com.danh.feature_artists.databinding.FragmentArtistListBinding
import com.danh.feature_artists.viewmodel.ArtistListViewModel


class ListArtist : Fragment() {
    private val viewmodel: ArtistListViewModel by viewModels()
    private lateinit var adapterArtist: ArtistAdapter
    private lateinit var binding: FragmentArtistListBinding
    private val navigator: ArtistsNavigation by lazy {
        requireActivity() as ArtistsNavigation
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentArtistListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
        viewmodel.listArtist()
    }

    private fun observeData() {
        viewmodel.artistList.observe(viewLifecycleOwner){list->
            if(list!=null){
                adapterArtist.updateView(list)
            }
        }
    }

    private fun setUpViews() {
        adapterArtist=ArtistAdapter(mutableListOf(),object: ArtistAdapter.OnClickItem{
            override fun clickItem(position: String) {
                navigator.openInformationArtist(position,this@ListArtist)

            }
        })
        binding.rcyArtist.layoutManager= LinearLayoutManager(requireContext())
        binding.rcyArtist.adapter=adapterArtist
        binding.tvTitleArtist.setOnClickListener {
            navigator.openMoreArtist(this@ListArtist)
        }
    }
}