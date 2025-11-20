package com.danh.core_navigation.home

import androidx.fragment.app.Fragment

interface AlbumsNavigation {
    fun openMoreAlbums(from: Fragment)
    fun openDetailAlbum(from: Fragment,id:String)
}