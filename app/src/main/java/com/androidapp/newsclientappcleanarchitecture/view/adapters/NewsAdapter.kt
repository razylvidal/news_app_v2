package com.androidapp.newsclientappcleanarchitecture.view.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.getPublishedDate
import com.androidapp.newsclientappcleanarchitecture.utils.getTimeDifference
import com.squareup.picasso.Picasso

class NewsAdapter(private val articles: MutableList<ArticleDetails>):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onClick: ((ArticleDetails) -> Unit)? = null

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById (R.id.tv_title)
        val descriptionTV: TextView = itemView.findViewById(R.id.tv_description)
        val publishedAtTV:TextView = itemView.findViewById(R.id.tv_publishedAt)
        val newsIV:ImageView = itemView.findViewById(R.id.iv_articleImage)
        val sourceTV:TextView = itemView.findViewById(R.id.tv_source)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.search_news_list_item,parent,false))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val selectedArticle: ArticleDetails = articles[position]
        holder.descriptionTV.text = selectedArticle.description
        holder.titleTV.text = selectedArticle.title
        holder.sourceTV.text = selectedArticle.source.name
        holder.publishedAtTV.apply {
            if (holder.itemView.context.toString().contains("SearchNews")){
                this.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    null)
                this.text = getPublishedDate(selectedArticle.publishedAt)
            }
            else{
                this.text = getTimeDifference(selectedArticle.publishedAt)
            }
        }

        Picasso.get().apply {
            if (selectedArticle.urlToImage == null) {
                this.load(R.drawable.no_image_available)
                    .into(holder.newsIV)
            } else {
                this.load(selectedArticle.urlToImage)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.no_image_available)
                    .into(holder.newsIV)
            }
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
        articles.addAll(articleList)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }
    fun onArticleCLicked(onClick: (ArticleDetails) -> Unit){
        this.onClick = onClick
    }
}