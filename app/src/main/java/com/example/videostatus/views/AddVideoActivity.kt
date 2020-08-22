package com.example.videostatus.views

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.videostatus.databinding.ActivityAddVideoBinding
import com.example.videostatus.base.BaseActivity
import com.example.videostatus.viewmodels.AddVideoViewModel
import kotlinx.android.synthetic.main.activity_add_video.*
import java.io.File
import kotlin.reflect.KClass


class AddVideoActivity : BaseActivity<AddVideoViewModel, ActivityAddVideoBinding>() {
    override val modelClass: KClass<AddVideoViewModel> = AddVideoViewModel::class

    override val layoutId: Int = com.example.videostatus.R.layout.activity_add_video


    override fun initControls() {

        selectLang.setOnClickListener {
            showDialog()
        }
        rlSelectVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"

            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), 1)
        }

    }



    private fun showDialog() {
        val factory = LayoutInflater.from(this)
        val deleteDialogView: View = factory.inflate(com.example.videostatus.R.layout.dialog_select_categoy, null)
        val deleteDialog: AlertDialog = AlertDialog.Builder(this).create()
        deleteDialog.setView(deleteDialogView)
        deleteDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, datas: Intent?) {
        super.onActivityResult(requestCode, resultCode, datas)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === 1) {
                val selectedImageUri: Uri? = datas!!.data

                // OI FILE Manager
                var filemanagerstring = selectedImageUri!!.getPath()

                // MEDIA GALLERY
                var selectedImagePath = getPath(selectedImageUri)
                if (selectedImagePath != null) {

                }
            }
        }
    }

    // UPDATED!
    fun getPath(uri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri!!, projection, null, null, null)
        return if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }

    fun checkFileSize(){
        val filepath: String =
            Environment.getExternalStorageDirectory().toString() + "/file.mp4"
        val file = File(filepath)
        var length = file.length()
        length = length / 1024
        Toast.makeText(
           this, "Video size:" + length + "KB",
            Toast.LENGTH_LONG
        ).show()
    }
}