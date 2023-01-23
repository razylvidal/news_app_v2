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
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.getPublishedDate
import com.androidapp.newsclientappcleanarchitecture.utils.getTimeDifference
import com.squareup.picasso.Picasso


@SuppressLint("NotifyDataSetChanged")
class CustomAdapter(private var articleList: MutableList<ArticleDetails>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var onClick: ((ArticleDetails) -> Unit)? = null
    private var onLongClick: ((Int) -> Unit)? = null
    private lateinit var context: Context

    init {
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedArticle: ArticleDetails = articleList[position]
        holder.headLine.text = selectedArticle.title
        holder.sourceTV.text = selectedArticle.source.name

        Picasso.get().apply {
            if (selectedArticle.urlToImage == null) {
                this.load(R.drawable.no_image_available)
                    .into(holder.image)
            } else {
                this.load(selectedArticle.urlToImage)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.no_image_available)
                    .into(holder.image)
            }
        }

        holder.newsPublicationTime.apply {
            if (holder.itemView.context.toString().contains("SavedNews")){
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

        holder.itemView.apply {
            this.setOnClickListener {
            onClick?.invoke(selectedArticle)
        }
            this.setOnLongClickListener {
                if(this.context.toString().contains("SavedNews")){
                    onLongClick?.invoke(position)
                }
                return@setOnLongClickListener true
            }
        }
    }
    override fun getItemCount(): Int {
        return articleList.size
    }

    class ViewHolder(
        ItemView: View,
    ) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_savedNews)
        val headLine: TextView = itemView.findViewById(R.id.tv_newsTitle)
        val newsPublicationTime: TextView = itemView.findViewById(R.id.tv_publicationTime)
        val sourceTV: TextView = itemView.findViewById(R.id.tv_saved_source)
    }
    fun onArticleCLicked(onClick: (ArticleDetails) -> Unit){
        this.onClick = onClick
    }
    fun onArticleLongCLicked(onLongCLick: (Int) -> Unit){
        this.onLongClick = onLongCLick
    }

    fun clear() {
        articleList.clear()
    }

    fun removeArticle(position: Int){
        articleList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun undoArticleRemoved(position: Int, articleDetails: ArticleDetails){
        articleList.add(position,articleDetails)
        notifyItemInserted(position)
    }
}
