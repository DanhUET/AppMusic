package com.danh.appmusic.navigition.discovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danh.core_navigation.library.ArtistsNavigation
import com.danh.feature_discovery.R

class AppArtistNavigation: ArtistsNavigation {
    override fun openMoreArtist(from: Fragment) {
        from.findNavController().navigate(R.id.action_discovery_to_allArtist)
    }

    override fun openInformationArtist(id:String,from: Fragment) {
        val bundle = Bundle().apply {
            putString("artistId", id)
        }
        from.findNavController().navigate(R.id.action_global_informationArtist,bundle)
    }
}