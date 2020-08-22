package com.example.videostatus.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.FavouiteDataModel
import com.example.videostatus.Retrofit.UserDataModel
import com.example.videostatus.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.file.attribute.UserDefinedFileAttributeView

class SignInViewModel(application: Application) : BaseViewModel(application) {

    var name : MutableLiveData<String> = MutableLiveData()
    var email : MutableLiveData<String> = MutableLiveData()
    var id : MutableLiveData<String> = MutableLiveData()
    var profileImageUrl : MutableLiveData<String> = MutableLiveData()

    var userSignInData : MutableLiveData<UserDataModel> = MutableLiveData()

    fun addUser(
        context: Context
    ) {
        val call: Call<UserDataModel> =
            RetrofitClass.getClient.addUser("add",id.value!!,email.value!!,profileImageUrl.value!!)

        call.enqueue(object : Callback<UserDataModel> {

            override fun onResponse(
                call: Call<UserDataModel>?,
                response: Response<UserDataModel>?
            ) {
                if (response != null) {

                    userSignInData.value = response.body()
                    Toast.makeText(context,(response.body() as UserDataModel).message,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserDataModel>?, t: Throwable?) {
                // hide loader
            }
        })


    }

}