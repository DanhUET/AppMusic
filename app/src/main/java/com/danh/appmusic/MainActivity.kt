package com.danh.appmusic

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.danh.appmusic.databinding.ActivityMainBinding
import com.danh.appmusic.navigition.home.AppAlbumNavigation
import com.danh.appmusic.navigition.home.AppRecommendedNavigator
import com.danh.core_navigation.home.AlbumsNavigation
import com.danh.core_navigation.home.RecommendedNavigation
import com.danh.feature_player.MiniPlayerHost

class MainActivity : AppCompatActivity(), RecommendedNavigation, AlbumsNavigation, MiniPlayerHost {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, 0)   // <-- bottom = 0
            insets  // KHÔNG CONSUME ở đây
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, bars.bottom)
            WindowInsetsCompat.CONSUMED
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // thiết lập bottom navigation
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun openMoreRecommended(from: Fragment) {
        AppRecommendedNavigator().openMoreRecommended(from)
    }

    override fun openMoreAlbums(from: Fragment) {
        AppAlbumNavigation().openMoreAlbums(from)
    }

    override fun openDetailAlbum(from: Fragment,id:String) {
        AppAlbumNavigation().openDetailAlbum(from,id)
    }

    override fun showMiniPlayer() {
        binding.fragmentMiniPlayer.visibility = View.VISIBLE
    }

    override fun hideMiniPlayer() {
       binding.fragmentMiniPlayer.visibility=View.GONE
    }
}
