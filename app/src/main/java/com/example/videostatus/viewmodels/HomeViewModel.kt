package com.example.videostatus.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.Retrofit.UserDataModel
import com.example.videostatus.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : BaseViewModel(application) {
    var CatData: MutableLiveData<CatLanDataModel> = MutableLiveData()
    fun getCategoyList( context: Context) {
        val call: Call<CatLanDataModel> =
            RetrofitClass.getClient.langCatApi("get")

        call.enqueue(object : Callback<CatLanDataModel> {

            override fun onResponse(
                call: Call<CatLanDataModel>?,
                response: Response<CatLanDataModel>?
            ) {
                if (response != null) {
                    //aa aypu che jo
                    CatData.value = response.body()


                } else {
                    Toast.makeText(context, CatData.value!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CatLanDataModel>?, t: Throwable?) {
                // hide loader
            }
        })
    }

    fun getUserDetails(
        context: Context,
        userId : String
    ) {
        val call: Call<UserDataModel> =
            RetrofitClass.getClient.getUserDetails("get",userId)

        call.enqueue(object : Callback<UserDataModel> {

            override fun onResponse(
                call: Call<UserDataModel>?,
                response: Response<UserDataModel>?
            ) {
                if (response != null) {

                    Toast.makeText(context,(response.body() as UserDataModel).message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserDataModel>?, t: Throwable?) {
                // hide loader
            }
        })
    }
}