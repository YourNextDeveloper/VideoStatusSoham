package com.example.videostatus.base

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.videostatus.R


enum class PermissionState {
    GRANTED,
    DENIED
}

abstract class RuntimePermissionViewModel(application: Application) :
    AndroidViewModel(application) {


    var context: Context? = null

    //Observe in activity
    val permissionRequest = MutableLiveData<PermissionState>()

    private lateinit var arrayPermission: ArrayList<String>
    var arrayListPermission: ArrayList<PermissionBean>?=null


    data class PermissionBean(val permission: String, var isAccept: Boolean)


    fun requestPermission(activity: Activity, vararg permissions: Permissions) {


        context = activity

        arrayListPermission = ArrayList()
        arrayPermission = ArrayList()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        activity, permission.value
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    arrayListPermission?.add(PermissionBean(permission.value, false))
                    arrayPermission.add(permission.value)
                }
            }

            arrayListPermission?.let {

                if (it.size <= 0) {

                    permissionRequest.postValue(PermissionState.GRANTED)

                    return
                }

            }



            activity.requestPermissions(
                arrayPermission.toArray(
                    arrayOfNulls<String>(
                        arrayPermission.size
                    )
                ), 10
            )
        } else {
            permissionRequest.postValue(PermissionState.GRANTED)
        }
    }


    private fun openSettingScreen() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        context?.startActivity(intent)
    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var rational = false
        for (i in permissions.indices) {
            updatePermissionResult(permissions[i], grantResults[i])
            context?.let {
                rational = ActivityCompat.shouldShowRequestPermissionRationale(
                    it as Activity,
                    permissions[i]
                )
            }
        }
        checkUpdate(rational)
    }

    private fun checkUpdate(rational: Boolean) {

        arrayListPermission?.let {

            var isGranted = true
            var deniedCount = 0
            for (i in it.indices) {
                if (!it[i].isAccept) {
                    isGranted = false
                    deniedCount++
                }
            }

            if (isGranted) {
                permissionRequest.postValue(PermissionState.GRANTED)
            } else {
                if (!rational) {
                    setAlertMessage()
                }
                permissionRequest.postValue(PermissionState.DENIED)
            }
        }


    }

    private fun updatePermissionResult(permissions: String, grantResults: Int) {

        arrayListPermission?.let {

            for (i in it.indices) {
                if (it[i].permission == permissions) {
                    it[i].isAccept = grantResults == 0
                    break
                }
            }

        }
    }

    private fun setAlertMessage() {

        val adb = AlertDialog.Builder(context)

        adb.setTitle(context?.resources?.getString(R.string.app_name))
        val msg = "<p>Dear User, </p>" +
                "<p>Seems like you have <b>\"Denied\"</b> the minimum requirement permission to access more features of application.</p>" +
                "<p>You must have to <b>\"Allow\"</b> all permission. We will not share your data with anyone else.</p>" +
                "<p>Do you want to enable all requirement permission ?</p>" +
                "<p>Go To : Settings >> App > " + context?.resources?.getString(R.string.app_name) + " Permissions : Allow ALL</p>"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adb.setMessage(Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY))
        } else {
            @Suppress("DEPRECATION")
            adb.setMessage(Html.fromHtml(msg))
        }
        adb.setPositiveButton("Allow All") { dialog, _ ->
            openSettingScreen()
            dialog.dismiss()
        }


        adb.setNegativeButton("Remind Me Later") { dialog, _ ->
            dialog.dismiss()
        }



        if (!(context as Activity).isFinishing) {
            adb.show()
        }
    }


}