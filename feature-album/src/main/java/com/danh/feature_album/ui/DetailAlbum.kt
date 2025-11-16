package com.danh.feature_album.ui

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
import com.danh.feature_album.R
import com.danh.feature_album.adapter.AlbumDetailAdapter
import com.danh.feature_album.databinding.FragmentDetailAlbumBinding
import com.danh.feature_album.viewmodel.AlbumDetailViewModel

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

    private fun setUpViews() {
        adapter= AlbumDetailAdapter(mutableListOf())
        val messageId = arguments?.getString("idAlbum", "0")
        val number=arguments?.getString("number")
        val image=arguments?.getString("image")
        val title= arguments?.getString("title")
        binding.rcyDetailAlbum.adapter=adapter
        binding.rcyDetailAlbum.layoutManager= LinearLayoutManager(requireContext())
        viewmodel= AlbumDetailViewModel(messageId!!)
        viewmodel.detailAlbumList.observe(viewLifecycleOwner){
            if(it!=null){
                binding.progress.visibility=View.GONE
                adapter.updateSongs(it)
            }else{
                binding.progress.visibility= View.VISIBLE
            }
        }
        Glide.with(binding.root).load(image).into(binding.imageAlbum)
        binding.title.text=title
        binding.number.text="Number of songs: ${number}"
        binding.progress.visibility= View.VISIBLE
        viewmodel.getDetailAlbum()
    }
}