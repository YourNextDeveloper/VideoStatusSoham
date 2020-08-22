package com.example.videostatus.viewmodels

import android.app.Application
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.mybaseapp.Retrofit.RetrofitClass
import com.example.videostatus.Retrofit.UserDataModel
import com.example.videostatus.base.BaseViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class SplashViewModel(application: Application) : BaseViewModel(application) {

    var splashNavigate: MutableLiveData<Boolean> = MutableLiveData()


}