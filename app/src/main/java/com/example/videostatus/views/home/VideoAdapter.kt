package com.example.videostatus.views.home

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.example.videostatus.databinding.ItemVideoBinding
import com.example.videostatus.Retrofit.CatLanDataModel

import com.example.videostatus.utils.layoutInflater
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VideoAdapter(
    private val context: Context,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<VideoAdapter.Item>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item = Item(ItemVideoBinding.inflate(parent.context.layoutInflater()))

    override fun getItemCount(): Int {
        return 10
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VideoAdapter.Item, position: Int) {

        holder.binding.tvVideoName.text = "Video Name $position"
        holder.binding.tvVideoDescription.text = "Video Description $position"
        holder.binding.tvVideoUploader.text = "Uploaded by : Uploader name $position"



        holder.itemView.setOnClickListener {
            callback(position)
        }
    }

    class Item(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

}