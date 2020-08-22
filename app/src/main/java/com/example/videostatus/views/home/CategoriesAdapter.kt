package com.example.videostatus.views.home

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.videostatus.databinding.ItemCategoryBinding
import com.example.videostatus.Retrofit.CatLanDataModel

import com.example.videostatus.utils.layoutInflater

class CategoriesAdapter(
    private val context: Context, private val catList:ArrayList<CatLanDataModel.DataCategory>,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.Item>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item =
        Item(
            ItemCategoryBinding.inflate(context.layoutInflater())
        )


    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: Item, position: Int) {

        holder.binding.tvCategoryName.text = catList[position].categoryName
        holder.binding.tvCategoryName.isSelected = true

        holder.itemView.setOnClickListener {
            callback(position)
        }
    }


    class Item(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

}