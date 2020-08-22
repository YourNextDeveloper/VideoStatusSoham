package com.example.mybaseapp.Retrofit

import com.example.videostatus.Retrofit.FavouiteDataModel
import com.example.videostatus.Retrofit.ReportDataModel
import com.example.videostatus.Retrofit.UserDataModel
import com.example.videostatus.Retrofit.VideoListModel
import com.example.videostatus.Retrofit.CatLanDataModel
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    //    @FormUrlEncoded
    @POST("get_category_language_data.php")
    fun langCatApi(@Query("action") action: String): Call<CatLanDataModel>

    @POST("get_video_list.php")
    fun getVideosApi(
        @Query("action") action: String,
        @Query("cid") cId: String
    ): Call<VideoListModel>

    //get favourite
    @POST("get_favourite_video.php")
    fun getFavouitesApi(
        @Query("action") action: String,
        @Query("uid") uid: String
    ): Call<FavouiteDataModel>

    //add favourite
    @POST("get_favourite_video.php")
    fun addFavouitesApi(
        @Query("action") action: String,
        @Query("user_id") user_id: String,
        @Query("video_id") video_id: String
    ): Call<FavouiteDataModel>

    //get user details
    @POST("get_favourite_video.php")
    fun getUserDetails(
        @Query("action") action: String,
        @Query("uid") uid: String
    ): Call<UserDataModel>

    //add user
    @POST("get_user_detail.php")
    fun addUser(
        @Query("action") action: String,
        @Query("user_name") user_id: String,
        @Query("user_email") userEmail: String,
        @Query("user_image") userImage: String
    ): Call<UserDataModel>

    //remove favourite
    @POST("get_favourite_video.php")
    fun removeFavouitesApi(
        @Query("action") action: String,
        @Query("favourite_id") favourite_id: String
    ): Call<FavouiteDataModel>

    //repot Video
    @POST("add_video_report.php")
    fun reportVideoApi(
        @Query("action") action: String,
        @Query("user_id") favourite_id: String,
        @Query("video_id") video_id: String,
        @Query("content_type") content_type: String,
        @Query("description") description: String
    ): Call<ReportDataModel>


}