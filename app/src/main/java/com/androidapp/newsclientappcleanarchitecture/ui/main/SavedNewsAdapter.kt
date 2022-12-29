package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.squareup.picasso.Picasso


@SuppressLint("NotifyDataSetChanged")
class SavedNewsAdapter(private var savedArticles: List<ArticleDetails>) :
    RecyclerView.Adapter<SavedNewsAdapter.ViewHolder>() {

    private var onClick: ((ArticleDetails) -> Unit)? = null
    private lateinit var context: Context
    private lateinit var mLongClickListener: OnItemLongClickListener

    init {
        this.notifyDataSetChanged()
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_news_list_item, parent, false)
        context = parent.context
        return ViewHolder(view, mLongClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedArticle: ArticleDetails = savedArticles[position]
        holder.headLine.text = selectedArticle.title
        holder.newsPublicationTime.text = StringBuilder().append("Date of Published: ")
            .append(selectedArticle.publishedAt)

        if(selectedArticle.urlToImage == null){
            Picasso.get().load(R.drawable.no_image_available).into(holder.image)
        }
        else {
            Picasso.get().load(selectedArticle.urlToImage)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image)
        }
        holder.itemView.setOnClickListener {
            onClick?.invoke(selectedArticle)
        }

    }

    override fun getItemCount(): Int {
        return savedArticles.size
    }

    class ViewHolder(
        ItemView: View,
        listener2: OnItemLongClickListener
    ) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_savedNews)
        val headLine: TextView = itemView.findViewById(R.id.tv_newsTitle)
        val newsPublicationTime: TextView = itemView.findViewById(R.id.tv_publicationTime)

        init {
            ItemView.setOnLongClickListener {
                listener2.onItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }
    fun onArticleCLicked(onClick: (ArticleDetails) -> Unit){
        this.onClick = onClick
    }

}
