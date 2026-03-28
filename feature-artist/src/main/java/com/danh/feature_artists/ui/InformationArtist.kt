package com.danh.feature_artists.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.danh.feature_artists.R
import com.danh.feature_artists.databinding.FragmentInfomationArtistBinding
import com.danh.feature_artists.viewmodel.ArtistListViewModel


class InformationArtist : Fragment() {
    private lateinit var binding: FragmentInfomationArtistBinding
    private lateinit var viewModel: ArtistListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentInfomationArtistBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
        val artistId = arguments?.getString("artistId")
        viewModel.informationArtist(artistId.toString())
    }

    private fun setUpViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val navController = findNavController()
        // 2. Thiết lập toolbar đi với NavController
        binding.toolbar.setupWithNavController(navController)
        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayShowTitleEnabled(false)
        viewModel= ArtistListViewModel()
        viewModel.artistInfo.observe(viewLifecycleOwner){
            if(it!=null){
                Glide.with(binding.root).load(it.avatar).into(binding.imgArtist)
                binding.tvTitleArtist.text="Artist name: ${it.name}"
                binding.tvTitleNumber.text="Number of interst: ${it.interested}"
            }
        }
    }
    private fun observeData(){

    }
}