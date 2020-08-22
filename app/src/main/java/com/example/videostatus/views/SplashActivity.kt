package com.example.videostatus.views

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Base64
import android.util.Log
import com.example.videostatus.databinding.ActivityMainBinding

import com.example.videostatus.base.BaseActivity

import com.example.videostatus.viewmodels.SplashViewModel
import com.example.videostatus.views.language.SelectLanguageActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.reflect.KClass

class SplashActivity : BaseActivity<SplashViewModel, ActivityMainBinding>() {

    override val modelClass: KClass<SplashViewModel> = SplashViewModel::class

    override val layoutId: Int = com.example.videostatus.R.layout.activity_main

    override fun initControls() {


        Handler().postDelayed({
            //dhraa

            goToActivity(this, SelectLanguageActivity::class)
            finish()

        }, 2000)
    }

}
