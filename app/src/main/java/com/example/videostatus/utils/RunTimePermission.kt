package com.example.videostatus.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.videostatus.R


class RunTimePermission(private var context: Context) {

    private lateinit var arrayPermission: ArrayList<String>
    private lateinit var arrayListPermission: ArrayList<PermissionBean>
    private lateinit var runTimePermissionListener: RunTimePermissionListener

    data class PermissionBean(val permission: String, var isAccept: Boolean)

    interface RunTimePermissionListener {
        fun permissionGranted()

        fun permissionDenied()
    }


    fun requestPermission(
        permissions: Array<String>,
        runTimePermissionListener: RunTimePermissionListener
    ) {

        this.runTimePermissionListener = runTimePermissionListener
        arrayListPermission = ArrayList()
        arrayPermission = ArrayList()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    arrayListPermission.add(PermissionBean(permission, false))
                    arrayPermission.add(permission)
                }
            }

            if (arrayListPermission.size <= 0) {
                runTimePermissionListener.permissionGranted()
                return
            }

            (context as Activity).requestPermissions(
                arrayPermission.toArray(
                    arrayOfNulls<String>(
                        arrayPermission.size
                    )
                ), 10
            )
        } else {
            runTimePermissionListener.permissionGranted()
        }
    }


    private fun openSettingScreen() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        for (i in permissions.indices) {
            updatePermissionResult(permissions[i], grantResults[i])
        }
        checkUpdate()
    }

    private fun checkUpdate() {
        var isGranted = true
        var deniedCount = 0
        for (i in arrayListPermission.indices) {
            if (!arrayListPermission[i].isAccept) {
                isGranted = false
                deniedCount++
            }
        }

        if (isGranted) {
            runTimePermissionListener.permissionGranted()
        } else {
            if (arrayListPermission.size > 1 && deniedCount == arrayListPermission.size) {
                setAlertMessage()
            }
        }
    }

    private fun updatePermissionResult(permissions: String, grantResults: Int) {
        for (i in arrayListPermission.indices) {
            if (arrayListPermission[i].permission == permissions) {
                arrayListPermission[i].isAccept = grantResults == 0
                break
            }
        }

    }

    fun setAlertMessage() {

        val adb = AlertDialog.Builder(context)

        adb.setTitle(context.resources?.getString(R.string.app_name))
        val msg = "<p>Dear User, </p>" +
                "<p>Seems like you have <b>\"Denied\"</b> the minimum requirement permission to access more features of application.</p>" +
                "<p>You must have to <b>\"Allow\"</b> all permission. We will not share your data with anyone else.</p>" +
                "<p>Do you want to enable all requirement permission ?</p>" +
                "<p>Go To : Settings >> App > " + context.resources?.getString(R.string.app_name) + " Permissions : Allow ALL</p>"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adb.setMessage(Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY))
        } else {
            @Suppress("DEPRECATION")
            adb.setMessage(Html.fromHtml(msg))
        }
        adb.setPositiveButton("Allow All") { dialog, _ ->
            openSettingScreen()
            dialog.dismiss()
            runTimePermissionListener.permissionDenied()
        }


        adb.setNegativeButton("Remind Me Later") { dialog, _ ->
            dialog.dismiss()
            runTimePermissionListener.permissionDenied()
        }



        if (!(context as Activity).isFinishing) {
            adb.show()
        }
    }
}