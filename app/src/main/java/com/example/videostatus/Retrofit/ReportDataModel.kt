package com.example.videostatus.Retrofit

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ReportDataModel {


    @SerializedName("result")
    @Expose
    var result = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data_report")
    @Expose
    var dataReport: DataReport? = null

    class DataReport {
        @SerializedName("result")
        @Expose
        var result = 0

        @SerializedName("message")
        @Expose
        var message: String? = null
    }

}