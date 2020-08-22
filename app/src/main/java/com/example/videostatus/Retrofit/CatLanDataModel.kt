package com.example.videostatus.Retrofit

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class CatLanDataModel() : Parcelable {
    @SerializedName("result")
    @Expose
    var result = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data_category")
    @Expose
    var dataCategory: List<DataCategory>? = null

    @SerializedName("data_language")
    @Expose
    var dataLanguage: List<DataLanguage>? = null

    class DataLanguage {
        @SerializedName("language_id")
        @Expose
        var languageId: String? = null

        @SerializedName("language_name")
        @Expose
        var languageName: String? = null
    }

    class DataCategory {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("category_name")
        @Expose
        var categoryName: String? = null
    }


}