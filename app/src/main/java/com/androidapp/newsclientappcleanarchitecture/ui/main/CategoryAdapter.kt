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

class CategoryAdapter(var category: MutableList<String>):
RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var selectedPosition: Int = 0
    private val presenter : MainContract.Presenter? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryRV: RelativeLayout = itemView.findViewById(R.id.idCVCategory)
        val categoryTV: TextView = itemView.findViewById(R.id.idTVCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_categories, parent, false)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = category[position]
        holder.categoryTV.text = category
        holder.itemView.setOnClickListener {
            val positionHolder = holder.adapterPosition
            presenter?.onCategoryClick(category)
            selectedPosition = positionHolder
            notifyDataSetChanged()
        }
        setCategoryTabColor(holder, selectedPosition, position)
    }

    override fun getItemCount(): Int {
        return category.size
    }
    private fun setCategoryTabColor(vHolder: ViewHolder, selectedCategoryTab: Int, position: Int){
        if (selectedCategoryTab == position){
            vHolder.categoryRV.setBackgroundColor(Color.RED)
            vHolder.categoryTV.setTextColor(Color.WHITE)
        }
        else{
            vHolder.categoryRV.setBackgroundColor(Color.WHITE)
            vHolder.categoryTV.setTextColor(Color.BLACK)
        }

    }
    fun updateCategoryData(categoryList: MutableList<String>){
       category.addAll(categoryList)
    }

}