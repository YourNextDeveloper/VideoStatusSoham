package com.example.videostatus.utils

import android.util.Log
import androidx.databinding.library.BuildConfig

object JLog {


    private val LOG = BuildConfig.DEBUG
    fun i(tag: String, string: String) {
        if (LOG) {
            Log.i(tag, string)
        }
    }

    fun e(tag: String, string: String) {
        if (LOG) {
            Log.e(tag, string)
        }
    }

    fun d(tag: String, string: String) {
        if (LOG) {
            Log.d(tag, string)
        }
    }

    fun v(tag: String, string: String) {
        if (LOG) {
            Log.v(tag, string)
        }
    }

    fun w(tag: String, string: String) {
        if (LOG) {
            Log.w(tag, string)
        }
    }

    fun isLog(): Boolean {
        return LOG
    }

}