package com.example.videostatus.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.VideoListModel
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoPlayerViewModel(application: Application) : BaseViewModel(application) {
    val videoData: MutableLiveData<VideoListModel> = MutableLiveData()
    fun getVideos(context: Context) {

        val call: Call<VideoListModel> =
            RetrofitClass.getClient.getVideosApi("get", "3")

        call.enqueue(object : Callback<VideoListModel> {

            override fun onResponse(
                call: Call<VideoListModel>?,
                response: Response<VideoListModel>?
            ) {
                if (response != null&&response.body()!!.dataVideoList!!.size>0) {

                    videoData.value = response.body()


                } else {
                    Toast.makeText(context, videoData.value!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<VideoListModel>?, t: Throwable?) {
                // hide loader
                Toast.makeText(context, videoData.value!!.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}