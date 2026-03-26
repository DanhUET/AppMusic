package com.danh.feature_artists.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danh.feature_artists.R
import com.danh.feature_artists.databinding.FragmentAllArtistBinding
import com.danh.feature_artists.viewmodel.ArtistListViewModel


class AllArtist : Fragment() {
    private lateinit var binding: FragmentAllArtistBinding
    private val viewModelAllArtist: ArtistListViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllArtistBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
        viewModelAllArtist.allArtist()
    }

    private fun setUpViews() {
        adapter= ArtistAdapter(mutableListOf(),object :ArtistAdapter.OnClickItem{
            override fun clickItem(position: String) {

            }

        })
        binding.rcy.adapter=adapter
        binding.rcy.layoutManager= LinearLayoutManager(requireContext())
        viewModelAllArtist.allArtist()
    }
    private fun observeData(){
        viewModelAllArtist.artistList.observe(viewLifecycleOwner){
            if(it!=null){
                adapter.updateView(it)
            }
        }
    }
}