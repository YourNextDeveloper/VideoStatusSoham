package com.example.videostatus.views.VideoPlayer

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.FavouiteDataModel
import com.example.videostatus.Retrofit.VideoListModel
import com.example.videostatus.databinding.ActivityVideoPlayerBinding
import com.example.videostatus.views.VideoPlayer.VideoPlayerAdapter
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.base.BaseActivity

import com.example.videostatus.viewmodels.VideoPlayerViewModel
import kotlinx.android.synthetic.main.activity_video_player.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.reflect.KClass

class VideoPlayerActivity : BaseActivity<VideoPlayerViewModel, ActivityVideoPlayerBinding>(){
    override val modelClass: KClass<VideoPlayerViewModel> = VideoPlayerViewModel::class

    override val layoutId: Int = com.example.videostatus.R.layout.activity_video_player
    var videoList: ArrayList<VideoListModel.DataVideoList> = ArrayList()
    private lateinit var adapter: VideoPlayerAdapter
    private lateinit var managet: LinearLayoutManager


    override fun initControls() {
        binding.lifecycleOwner = this
        setUpList()
        addObsever()
        viewModel.getVideos(this)
    }

    private fun addObsever() {

        viewModel.videoData.observe(this, Observer {
            videoList.addAll((it as VideoListModel).dataVideoList!!)
            setUpList()
        })
    }


    private fun setUpList() {
        managet = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
        adapter = VideoPlayerAdapter(this, videoList){ position: Int->



        }

        rvVideos.layoutManager = managet
        rvVideos.adapter = adapter
    }

    private fun addToFavorite() {
        val call: Call<FavouiteDataModel> =
            RetrofitClass.getClient.addFavouitesApi("add", "", "")

        call.enqueue(object : Callback<FavouiteDataModel> {

            override fun onResponse(
                call: Call<FavouiteDataModel>?,
                response: Response<FavouiteDataModel>?
            ) {
                if (response != null) {



                } else {


                }
            }

            override fun onFailure(call: Call<FavouiteDataModel>?, t: Throwable?) {
                // hide loader
            }
        })

    }
}