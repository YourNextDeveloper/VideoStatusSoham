package com.example.videostatus.views

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videostatus.databinding.ActivityFavouritesBinding
import com.example.videostatus.base.BaseActivity

import com.example.videostatus.viewmodels.FavouiteViewModel
import com.example.videostatus.views.VideoPlayer.VideoPlayerActivity
import com.example.videostatus.views.home.CategoriesAdapter
import com.example.videostatus.views.home.VideoAdapter
import kotlinx.android.synthetic.main.activity_favourites.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.reflect.KClass

class FavouritesActivity : BaseActivity<FavouiteViewModel, ActivityFavouritesBinding>() {
    override val modelClass: KClass<FavouiteViewModel> = FavouiteViewModel::class

    override val layoutId: Int = com.example.videostatus.R.layout.activity_favourites

    private lateinit var videoAdapter: VideoAdapter
    private lateinit var manager: GridLayoutManager

    override fun initControls() {
        binding.lifecycleOwner = this
        addObserver()
        viewModel.getFavourites(this)
    }

    private fun addObserver() {
        viewModel.favData.observe(this, Observer {
            initVideoList()
        })
    }

    private fun initVideoList() {

        videoAdapter = VideoAdapter(this) {

        }

        rvFavouites.layoutManager = manager
        rvFavouites.adapter = videoAdapter
    }

}