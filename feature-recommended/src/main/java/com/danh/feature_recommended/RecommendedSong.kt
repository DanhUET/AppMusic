package com.danh.feature_recommended

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danh.core_navigation.home.RecommendedNavigation
import com.danh.feature_recommended.adapter.RSAdapter
import com.danh.feature_recommended.databinding.FragmentRecommendedSongBinding
import com.danh.feature_recommended.viewmodel.RSViewModel
import com.google.android.material.snackbar.Snackbar
class RecommendedSong( ) : Fragment() {
    private lateinit var navigator: RecommendedNavigation
    private lateinit var binding: FragmentRecommendedSongBinding
    private lateinit var recommendedSongAdapter: RSAdapter
    private lateinit var recommendedViewModel : RSViewModel
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
        recommendedViewModel.getRecommendedSong()

        binding.btnAllSong.setOnClickListener {
            navigator.openMoreRecommended(this)
        }

    }

    private fun setUpViews() {
        recommendedSongAdapter= RSAdapter(mutableListOf())
        binding.rcyRS.adapter=recommendedSongAdapter
        binding.rcyRS.layoutManager = LinearLayoutManager(requireContext())
        recommendedViewModel= RSViewModel()
        recommendedViewModel.listSong.observe(viewLifecycleOwner){
            if(it!=null){
                recommendedSongAdapter.updateSongs(it)
            }else{
                Snackbar.make(
                    binding.root,
                    "cc",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Activity host (MainActivity) phải implement RecommendedNavigation
        if (context is RecommendedNavigation) {
            navigator = context
        } else {
            throw IllegalStateException(
                "Host Activity must implement RecommendedNavigation"
            )
        }
    }


}