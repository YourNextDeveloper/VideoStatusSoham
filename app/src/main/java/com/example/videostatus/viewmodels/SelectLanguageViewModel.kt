package com.example.videostatus.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectLanguageViewModel(application: Application) : BaseViewModel(application) {

    var langCatData: MutableLiveData<CatLanDataModel> = MutableLiveData()



    fun getCatLang(
        context: Context

    ) {
        val call: Call<CatLanDataModel> =
            RetrofitClass.getClient.langCatApi("get")

        call.enqueue(object : Callback<CatLanDataModel> {

            override fun onResponse(
                call: Call<CatLanDataModel>?,
                response: Response<CatLanDataModel>?
            ) {
                if (response != null) {
                    //aa aypu che jo
                    langCatData.value = response.body()


                } else {
                    Toast.makeText(context, langCatData.value!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CatLanDataModel>?, t: Throwable?) {
                // hide loader
            }
        })


    }

}