package com.example.videostatus.views

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videostatus.databinding.ActivityDownloadBinding
import com.example.videostatus.base.BaseActivity

import com.example.videostatus.viewmodels.DownloadViewModel
import com.example.videostatus.views.VideoPlayer.VideoPlayerActivity
import com.example.videostatus.views.home.CategoriesAdapter
import com.example.videostatus.views.home.VideoAdapter
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.activity_favourites.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.reflect.KClass

class DownloadsActivity : BaseActivity<DownloadViewModel, ActivityDownloadBinding>() {
    override val modelClass: KClass<DownloadViewModel> = DownloadViewModel::class

    override val layoutId: Int = com.example.videostatus.R.layout.activity_download

    private lateinit var videoAdapter: VideoAdapter
    private lateinit var manager: GridLayoutManager

    override fun initControls() {
        initVideoList()
    }
    private fun initVideoList() {

        videoAdapter = VideoAdapter(this) {

        }

        rvDownloads.layoutManager = manager
        rvDownloads.adapter = videoAdapter
    }

}