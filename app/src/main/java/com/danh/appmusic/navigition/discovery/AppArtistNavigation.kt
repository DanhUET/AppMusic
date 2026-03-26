package com.danh.appmusic.navigition.discovery

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danh.core_navigation.library.ArtistsNavigation
import com.danh.feature_discovery.R

class AppArtistNavigation: ArtistsNavigation {
    override fun openMoreArtist(from: Fragment) {
        from.findNavController().navigate(R.id.action_discovery_to_allArtist)
    }

    override fun openInformationArtist() {
        TODO("Not yet implemented")
    }
}