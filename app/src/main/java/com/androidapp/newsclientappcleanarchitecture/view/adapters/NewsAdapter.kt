package com.androidapp.newsclientappcleanarchitecture.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.utils.LogHelper
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.getPublishedDate
import com.androidapp.newsclientappcleanarchitecture.utils.getTimeDifference
import com.squareup.picasso.Picasso

class NewsAdapter(private val articles: MutableList<ArticleDetails>, val context: Context):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onClick: ((ArticleDetails) -> Unit)? = null

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById (R.id.tv_title)
        val descriptionTV: TextView = itemView.findViewById(R.id.tv_description)
        val publishedAtTV:TextView = itemView.findViewById(R.id.tv_publishedAt)
        val newsIV:ImageView = itemView.findViewById(R.id.iv_articleImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.main_news_list_item,parent,false))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val selectedArticle: ArticleDetails = articles[position]
        holder.descriptionTV.text = selectedArticle.description
        holder.titleTV.text = selectedArticle.title
        if (context.toString().contains("SearchNews")){
            holder.publishedAtTV.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                null,
                null)
            holder.publishedAtTV.text = getPublishedDate(selectedArticle.publishedAt)
        }
        else{
            holder.publishedAtTV.text = getTimeDifference(selectedArticle.publishedAt)
        }

        if(selectedArticle.urlToImage == null){
            Picasso.get().load(R.drawable.no_image_available).into(holder.newsIV)
        }
        else{
            Picasso.get().load(selectedArticle.urlToImage)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.newsIV)
        }

        holder.itemView.setOnClickListener {
            onClick?.invoke(selectedArticle)
        }
    }
    override fun getItemCount(): Int {
        return articles.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateArticleData(articleList: List<ArticleDetails>) {
        LogHelper.log("adapter", articleList.size.toString())
        articles.addAll(articleList)
        notifyDataSetChanged()
    }
    fun clear() {
        articles.clear()
    }
    fun onArticleCLicked(onClick: (ArticleDetails) -> Unit){
        this.onClick = onClick
    }
}