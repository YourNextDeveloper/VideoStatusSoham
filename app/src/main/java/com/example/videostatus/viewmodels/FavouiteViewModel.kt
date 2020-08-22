package com.example.videostatus.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.FavouiteDataModel
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouiteViewModel (application: Application) : BaseViewModel(application) {
    var favData: MutableLiveData<FavouiteDataModel> = MutableLiveData()
    fun getFavourites(
        context: Context

    ) {
        val call: Call<FavouiteDataModel> =
            RetrofitClass.getClient.getFavouitesApi("get","")

        call.enqueue(object : Callback<FavouiteDataModel> {

            override fun onResponse(
                call: Call<FavouiteDataModel>?,
                response: Response<FavouiteDataModel>?
            ) {
                if (response != null) {
                    //aa aypu che jo
                    favData.value = response.body()


                } else {
                    Toast.makeText(context, favData.value!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<FavouiteDataModel>?, t: Throwable?) {
                // hide loader
            }
        })


    }
}