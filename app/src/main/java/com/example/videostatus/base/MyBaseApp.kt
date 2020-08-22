package com.example.videostatus.base


import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.example.videostatus.utils.PreferenceHelper
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

//import com.amazonaws.mobile.client.AWSMobileClient


class MyBaseApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this)
        PreferenceHelper.init(this)
    }

    companion object {
        private var instance: MyBaseApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        var tempPref: SharedPreferences? = null
        fun getPref(): SharedPreferences? {
            return tempPref
        }

        var context: Context? = null
        fun getContextApp(): Context? {
            return context;
        }
    }
}