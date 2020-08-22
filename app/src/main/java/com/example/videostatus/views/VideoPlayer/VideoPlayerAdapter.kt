package com.example.videostatus.views.VideoPlayer

import android.Manifest
import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.videostatus.Retrofit.VideoListModel
import com.example.videostatus.databinding.ItemVideosBinding
import com.example.videostatus.utils.Constants
import com.example.videostatus.base.MyBaseApp.Companion.context
import com.example.videostatus.utils.layoutInflater
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class VideoPlayerAdapter(
    var context: Context,
    var videoList: ArrayList<VideoListModel.DataVideoList>,
    private val callback: (position: Int) -> Unit
) :
    RecyclerView.Adapter<VideoPlayerAdapter.ViewHolder>() {
    val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoPlayerAdapter.ViewHolder {
        return ViewHolder(ItemVideosBinding.inflate(parent.context.layoutInflater()))
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: VideoPlayerAdapter.ViewHolder, position: Int) {

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        holder.binding.andExoPlayerView.minimumHeight = height
        holder.binding.andExoPlayerView.minimumWidth = width
        holder.binding.andExoPlayerView.setSource(videoList[position].video)
        holder.binding.fav.setOnClickListener {
            callback(holder.adapterPosition)
        }
        holder.binding.download.setOnClickListener {
            //  newDownload(urlString)
        }


    }

    class ViewHolder(val binding: ItemVideosBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}

/*  //hare you can start downloding video
  fun newDownload(url: String?) {
      val downloadTask = DownloadTask(context as Activity)
      downloadTask.execute(url)
  }

  class DownloadTask(context: Context) : AsyncTask<String, Int, String>() {
      private lateinit var mWakeLock : PowerManager.WakeLock

      //        val bnp: NumberPro
      override fun doInBackground(vararg p0: String?): String? {
          var input: InputStream? = null
          var output: OutputStream? = null
          var connection: HttpURLConnection? = null
          try {
              val url = URL(p0[0])
              connection = url.openConnection() as HttpURLConnection
              connection.connect()
              if (connection.getResponseCode() !== HttpURLConnection.HTTP_OK) {
                  return ("Server returned HTTP " + connection.getResponseCode()
                          + " " + connection.getResponseMessage())
              }
              val fileLength = connection.getContentLength()
              input = connection.getInputStream()
              var fileN = "FbDownloader_" + UUID.randomUUID().toString().substring(0, 10) + ".mp4"
              val filename = File(
                  (Environment.getExternalStorageDirectory()
                      .getAbsolutePath() + Constants.FOLDER_NAME), fileN
              )
              output = FileOutputStream(filename)
              var data = ByteArray(4096)
              var total: Long = 0
              var count: Int = input.read(data)
              while (count !== -1) {
                  if (isCancelled()) {
                      input.close()
                      return null
                  }
                  total += count
                  if (fileLength > 0)
                      publishProgress((total * 100 / fileLength) as Int)
                  output.write(data, 0, count)
              }
          } catch (e: Exception) {
              return e.toString()
          } finally {
              try {
                  if (output != null)
                      output.close()
                  if (input != null)
                      input.close()
              } catch (ignored: IOException) {
              }
              if (connection != null)
                  connection.disconnect()
          }
          return null
      }


      override fun onProgressUpdate(vararg values: Int?) {
          super.onProgressUpdate(*values)
          // bnp.setProgress(values[0])
      }

      override fun onPreExecute() {
          super.onPreExecute()
          val pm = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
          mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, VideoPlayerAdapter::class.java.simpleName)
          mWakeLock.acquire()
          val dialogLayout = LayoutInflater.from(context as Activity)
          val DialogView = dialogLayout.inflate(R.layout.progress_dialog, null)
          var downloadDialog = Dialog(context as Activity, R.style.CustomAlertDialog)
          downloadDialog.setContentView(DialogView)
          val lp = WindowManager.LayoutParams()
          lp.copyFrom(downloadDialog.getWindow()!!.getAttributes())
          lp.width = ((context as Activity).resources.getDisplayMetrics().widthPixels)
          lp.height =
              ((context as Activity).resources.getDisplayMetrics().heightPixels * 0.65) as Int
          downloadDialog.window!!.setAttributes(lp)
          val cancel = DialogView.findViewById(R.id.cancel_btn) as Button
          cancel.setOnClickListener(object : View.OnClickListener {
              override fun onClick(v: View) {
                  //stopping the Asynctask
                  cancel(true)
                  downloadDialog.dismiss()
              }
          })
          downloadDialog.setCancelable(false)
          downloadDialog.setCanceledOnTouchOutside(false)
          // bnp = DialogView.findViewById(R.id.number_progress_bar) as NumberProgressBar
          // bnp.setProgress(0)
          // bnp.setMax(100)
          downloadDialog.show()
      }

      override fun onPostExecute(result: String?) {
          super.onPostExecute(result)
          mWakeLock.release()
          downloadDialog.dismiss()
          if (result != null)
              Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show()
          else
              Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show()
          MediaScannerConnection.scanFile(context as Activity,
              arrayOf<String>(
                  (Environment.getExternalStorageDirectory().getAbsolutePath() +
                          Constants.FOLDER_NAME + fileN)
              ), null,
              object : MediaScannerConnection.OnScanCompletedListener {
                 override fun onScanCompleted(newpath: String, newuri: Uri) {
                      Log.i("ExternalStorage", "Scanned " + newpath + ":")
                      Log.i("ExternalStorage", "-> uri=" + newuri)
                  }
              })
      }
  }
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
fun checkPermission(): Boolean {
  val currentAPIVersion = Build.VERSION.SDK_INT
  return if (currentAPIVersion >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(
              context as Activity,
              Manifest.permission.WRITE_EXTERNAL_STORAGE
          ) != PackageManager.PERMISSION_GRANTED
      ) {
          if (ActivityCompat.shouldShowRequestPermissionRationale(
                  context as Activity,
                  Manifest.permission.WRITE_EXTERNAL_STORAGE
              )
          ) {
              val alertBuilder =
                  AlertDialog.Builder(context as Activity)
              alertBuilder.setCancelable(true)
              alertBuilder.setTitle("Permission necessary")
              alertBuilder.setMessage("Write Storage permission is necessary to Download Images and Videos!!!")
              alertBuilder.setPositiveButton(
                  R.string.yes
              ) { dialog, which ->
                  ActivityCompat.requestPermissions(
                      context as Activity,
                      arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                      MY_PERMISSIONS_REQUEST_WRITE_STORAGE
                  )
              }
              val alert = alertBuilder.create()
              alert.show()
          } else {
              ActivityCompat.requestPermissions(
                  context as Activity,
                  arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                  MY_PERMISSIONS_REQUEST_WRITE_STORAGE
              )
          }
          false
      } else {
          true
      }
  } else {
      true
  }
}

//Here you can check App Permission
fun onRequestPermissionsResult(
  requestCode: Int,
  permissions: Array<String?>?,
  grantResults: IntArray
) {
  when (requestCode) {
      MY_PERMISSIONS_REQUEST_WRITE_STORAGE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          checkFolder()
      } else {
          //code for deny
          checkAgain()
      }
  }
}
fun checkAgain() {
  if (ActivityCompat.shouldShowRequestPermissionRationale(
          context as Activity,
          Manifest.permission.WRITE_EXTERNAL_STORAGE
      )
  ) {
      val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(context as Activity)
      alertBuilder.setCancelable(true)
      alertBuilder.setTitle("Permission necessary")
      alertBuilder.setMessage("Write Storage permission is necessary to Download Images and Videos!!!")
      alertBuilder.setPositiveButton(
          R.string.yes,
          DialogInterface.OnClickListener { dialog, which ->
              ActivityCompat.requestPermissions(
                  context as Activity,
                  arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                  MY_PERMISSIONS_REQUEST_WRITE_STORAGE
              )
          })
      val alert: AlertDialog = alertBuilder.create()
      alert.show()
  } else {
      ActivityCompat.requestPermissions(
          context as Activity,
          arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
          MY_PERMISSIONS_REQUEST_WRITE_STORAGE
      )
  }
}*/

//hare you can check folfer whare you want to store download Video
/*
fun checkFolder() {
    val path: String = Environment.getExternalStorageDirectory().getAbsolutePath()
        .toString() + "/FBDownloader/"
    val dir = File(path)
    var isDirectoryCreated: Boolean = dir.exists()
    if (!isDirectoryCreated) {
        isDirectoryCreated = dir.mkdir()
    }
    if (isDirectoryCreated) {
        // do something\
        Log.d("Folder", "Already Created")
    }
}
*/

