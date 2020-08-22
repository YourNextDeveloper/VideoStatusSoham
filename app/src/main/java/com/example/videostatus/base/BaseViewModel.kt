package com.example.videostatus.base

import android.app.Application
import com.example.videostatus.utils.PreferenceHelper.customPrefs

abstract class BaseViewModel(application: Application) : RuntimePermissionViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName

    val prefs by lazy {
        application.customPrefs()
    }

}
