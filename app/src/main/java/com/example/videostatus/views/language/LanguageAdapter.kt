package com.example.videostatus.views.language

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.videostatus.R
import com.example.videostatus.databinding.ItemLanguageBinding

import com.example.videostatus.Retrofit.CatLanDataModel

import com.example.videostatus.utils.layoutInflater

class LanguageAdapter(val context: Context,val list:ArrayList<CatLanDataModel.DataLanguage>) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    val arrayListBg = ArrayList<Drawable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageAdapter.ViewHolder {
        return ViewHolder(ItemLanguageBinding.inflate(parent.context.layoutInflater()))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: LanguageAdapter.ViewHolder, position: Int) {
        arrayListBg.add(context.getDrawable(R.drawable.gredient_one))
        arrayListBg.add(context.getDrawable(R.drawable.gredient_five))
        arrayListBg.add(context.getDrawable(R.drawable.gredient_three))
        arrayListBg.add(context.getDrawable(R.drawable.gredient_four))
        arrayListBg.add(context.getDrawable(R.drawable.gredient_two))
        arrayListBg.add(context.getDrawable(R.drawable.gredient_six))
        arrayListBg.add(context.getDrawable(R.drawable.gredient_seven))

        holder.binding.tvLanText.text = list[position].languageName
        holder.binding.cvBg.background = arrayListBg[position]
         /* when (position) {

              0 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_one)
                 // holder.binding.tvLanText.text = "English"
                  holder.binding.tvLanText.text = list[position].languageName

              }
              1 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_five)
                  holder.binding.tvLanText.text = "Hindi"
              }
              2 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_three)
                  holder.binding.tvLanText.text = "Marathi"
              }
              3 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_four)
                  holder.binding.tvLanText.text = "Bengoli"
              }
              4 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_two)
                  holder.binding.tvLanText.text = "Punjabi"
              }
              5 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_six)
                  holder.binding.tvLanText.text = "Bhojpuri"
              }
              6 -> {
                  holder.binding.cvBg.background = context.getDrawable(R.drawable.gredient_seven)
                  holder.binding.tvLanText.text = "Tamil"
              }


          }*/
    }

    class ViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}