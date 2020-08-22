package com.example.videostatus.Retrofit

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FavouiteDataModel {

    @SerializedName("result")
    @Expose
    var result = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data_favourite_video_list")
    @Expose
    var dataFavouriteVideoList: List<DataFavouriteVideoList>? = null

    class DataFavouriteVideoList {
        @SerializedName("result")
        @Expose
        var result: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("favourite_id")
        @Expose
        var favouriteId: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

        @SerializedName("video_name")
        @Expose
        var videoName: String? = null

        @SerializedName("video")
        @Expose
        var video: String? = null
    }
}