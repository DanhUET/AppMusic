package com.danh.appmusic.navigition.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danh.core_navigation.home.AlbumsNavigation
import com.danh.feature_home.R

class AppAlbumNavigation : AlbumsNavigation {
    override fun openMoreAlbums(from: Fragment) {
        from.findNavController().navigate(R.id.action_fragment_home_to_fragment_more_albums)
    }

    override fun openDetailAlbum(from: Fragment, id: String) {
        val bundle = Bundle().apply {
            putString("idAlbum", id)
        }
        from.findNavController()
            .navigate(com.danh.appmusic.R.id.action_global_fr_album_detail, bundle)
    }
}