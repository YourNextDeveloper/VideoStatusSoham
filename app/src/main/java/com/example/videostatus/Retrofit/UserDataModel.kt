package com.example.videostatus.Retrofit

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UserDataModel {
    @SerializedName("result")
    @Expose
    var result = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data_user_detail")
    @Expose
    var dataUserDetail: List<DataUserDetail>? = null

    class DataUserDetail {
        @SerializedName("result")
        @Expose
        var result = 0

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

        @SerializedName("user_email")
        @Expose
        var userEmail: String? = null

        @SerializedName("user_image")
        @Expose
        var userImage: String? = null
    }
}