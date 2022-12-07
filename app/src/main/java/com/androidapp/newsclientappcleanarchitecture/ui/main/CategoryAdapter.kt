package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category

class CategoryAdapter(var category: MutableList<Category>,
                      private val onClick: (String) -> Unit):
RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition: Int = 0

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryRV: RelativeLayout = itemView.findViewById(R.id.idCVCategory)
        val categoryTV: TextView = itemView.findViewById(R.id.idTVCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_categories, parent, false)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryName = category[position].name
        holder.categoryTV.text = categoryName
        holder.itemView.setOnClickListener {
            val positionHolder = holder.adapterPosition
            onClick.invoke(categoryName)
            selectedPosition = positionHolder
            notifyDataSetChanged()
        }
        setCategoryTabColor(holder, selectedPosition, position)
    }

    override fun getItemCount(): Int {
        return category.size
    }
    private fun setCategoryTabColor(vHolder: CategoryViewHolder, selectedCategoryTab: Int, position: Int){
        if (selectedCategoryTab == position){
            vHolder.categoryRV.setBackgroundColor(Color.RED)
            vHolder.categoryTV.setTextColor(Color.WHITE)
        }
        else{
            vHolder.categoryRV.setBackgroundColor(Color.WHITE)
            vHolder.categoryTV.setTextColor(Color.BLACK)
        }

    }
    fun updateCategoryData(categoryList: List<Category>){
       category.addAll(categoryList.toMutableList())
    }

}