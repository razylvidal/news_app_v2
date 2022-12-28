package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.squareup.picasso.Picasso

class NewsAdapter(private val articles: MutableList<ArticleDetails>, private val context: Context):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById (R.id.tv_title)
        val descriptionTV: TextView = itemView.findViewById(R.id.tv_description)
        val publishedAtTV:TextView = itemView.findViewById(R.id.tv_publishedAt)
        val newsIV:ImageView = itemView.findViewById(R.id.iv_articleImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.activity_news,parent,false))
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articles: ArticleDetails = articles[position]
        holder.descriptionTV.text = articles.description
        holder.titleTV.text = articles.title
        holder.publishedAtTV.text = StringBuilder().append("Date of Published: ")
            .append(articles.publishedAt)

        if(articles.urlToImage == null){
            Picasso.get().load(R.drawable.no_image_available).into(holder.newsIV)
        }
        else{
            Picasso.get().load(articles.urlToImage)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.newsIV)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReadFullNewsActivity::class.java)
            intent.putExtra("title", articles.title)
            intent.putExtra("author", articles.author)
            intent.putExtra("publishedAt", articles.publishedAt)
            intent.putExtra("content", articles.content)
            intent.putExtra("description", articles.description)
            intent.putExtra("image", articles.urlToImage)
            intent.putExtra("url", articles.url)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return articles.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateArticleData(articleList: List<ArticleDetails>) {
        articles.addAll(articleList)
    }
    fun clear() {
        articles.clear()
    }
}