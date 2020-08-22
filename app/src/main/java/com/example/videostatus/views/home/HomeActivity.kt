package com.example.videostatus.views.home

import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videostatus.R
import com.example.videostatus.databinding.ActivityHomeBinding
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.Retrofit.UserDataModel

import com.example.videostatus.base.BaseActivity
import com.example.videostatus.utils.PreferenceHelper
import com.example.videostatus.utils.USER_DATA

import com.example.videostatus.utils.showToast
import com.example.videostatus.viewmodels.AddVideoViewModel
import com.example.videostatus.viewmodels.HomeViewModel
import com.example.videostatus.views.DownloadsActivity
import com.example.videostatus.views.FavouritesActivity
import com.example.videostatus.views.VideoPlayer.VideoPlayerActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.reflect.KClass

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    override val modelClass: KClass<HomeViewModel> = HomeViewModel::class
    override val layoutId: Int = com.example.videostatus.R.layout.activity_home

    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var videoAdapter: VideoAdapter

    private lateinit var manager: GridLayoutManager
    var catList: ArrayList<CatLanDataModel.DataCategory> = ArrayList()


    override fun initControls() {

        binding.lifecycleOwner = this

        manager = GridLayoutManager(this, 2)

        homeNavigation.inflateMenu(R.menu.drawer_items)
        ivUpload.setOnClickListener {
            goToActivity(this, AddVideoViewModel::class)
        }
        ivMenu.setOnClickListener {
            if (homeDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                homeDrawerLayout.closeDrawers()
            } else {
                homeDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        ivsearch.setOnClickListener {
            showToast(this, "Coming Soon")
        }


        /*  homeNavigation.setNavigationItemSelectedListener {

              when (it.itemId) {

                  R.id.menu_item_downloads -> {
                      goToActivity(this, DownloadsActivity::class)
                  }

                  R.id.menu_item_favorites -> {
                      goToActivity(this, FavouritesActivity::class)
                  }


              }
              homeDrawerLayout.closeDrawers()
              return@setNavigationItemSelectedListener true
          }*/

       initVideoList()

        addObsever()

        viewModel.getCategoyList(this)

        if (PreferenceHelper.getUserObject() != null){
            val userId = PreferenceHelper.getUserObject()!!.dataUserDetail!![0].userId
            viewModel.getUserDetails(this,userId!!)
        }
    }

    private fun addObsever() {
        viewModel.CatData.observe(this, mObserver)
    }

    val mObserver = Observer<Any> {

        if (it is CatLanDataModel) {
            catList.addAll((it as CatLanDataModel).dataCategory!!)
            initCategories()
        }else if (it is UserDataModel){
            PreferenceHelper.saveObject(USER_DATA,it)
        }
    }

    private fun initCategories() {

        categoryAdapter = CategoriesAdapter(this, catList) {
            goToActivity(this, VideoPlayerActivity::class)
        }

        rvCategories.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvCategories.adapter = categoryAdapter
    }

    private fun initVideoList() {

        videoAdapter = VideoAdapter(this) {

        }

        rvVideos.layoutManager = manager
        rvVideos.adapter = videoAdapter
    }

}
