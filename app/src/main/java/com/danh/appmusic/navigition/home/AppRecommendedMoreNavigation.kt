package com.danh.appmusic.navigition.home

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.danh.core_navigation.home.RecommendedNavigation
import com.danh.feature_home.R

class AppRecommendedMoreNavigation : RecommendedNavigation{
    override fun openMoreRecommended(from: Fragment) {
        from.findNavController().navigate(R.id.action_home2_to_fragment_more_recommended)
    }
}