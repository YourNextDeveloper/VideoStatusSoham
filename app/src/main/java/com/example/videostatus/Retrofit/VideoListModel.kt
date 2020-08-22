package com.example.videostatus.Retrofit

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class VideoListModel  {
    @SerializedName("result")
    @Expose
    var result = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data_video_list")
    @Expose
    var dataVideoList: List<DataVideoList>? = null

    class DataVideoList {
        @SerializedName("result")
        @Expose
        var result: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("video_category")
        @Expose
        var videoCategory: String? = null

        @SerializedName("video_name")
        @Expose
        var videoName: String? = null

        @SerializedName("video")
        @Expose
        var video: String? = null
    }
}