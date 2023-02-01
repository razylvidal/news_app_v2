package com.androidapp.newsclientappcleanarchitecture.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.*
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
    private var onMultiSelect: ((Int) -> Unit)? = null
    private var onHideToolbar: ((Boolean) -> Unit)? = null
    private lateinit var context: Context
    private var articlesToRemove: MutableList<ArticleDetails> = mutableListOf()
    private var isEnable = false
    private var isSelectAll = false
    private var selectedItemSize = 0

    class ViewHolder(
        ItemView: View,
    ) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_savedNews)
        val headLine: TextView = itemView.findViewById(R.id.tv_newsTitle)
        val newsPublicationTime: TextView = itemView.findViewById(R.id.tv_publicationTime)
        val sourceTV: TextView = itemView.findViewById(R.id.tv_saved_source)
        val checkBox: ImageView = itemView.findViewById(R.id.check_box)
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
            if (selectedArticle.urlToImage.toString() == "null")
                this.load(R.drawable.breaking_news)
            else {
                this.load(selectedArticle.urlToImage)
                    .placeholder(R.drawable.placeholder_image)
            }.into(holder.image)
        }

        holder.newsPublicationTime.apply {
            if (holder.itemView.context.toString().contains("SavedNews")) {
                this.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    null
                )
                this.text = getPublishedDate(selectedArticle.publishedAt)
            } else {
                this.text = getTimeDifference(selectedArticle.publishedAt)
            }
        }

        holder.itemView.apply {
            this.setOnClickListener {
                if (isEnable)
                    clickedItem(holder)
                else
                    onClick?.invoke(selectedArticle)
            }
            this.setOnLongClickListener {
                if (this.context.toString().contains("SavedNews")) {
                    onHideToolbar?.invoke(true)
                    if (!isEnable) {
                        val callback: ActionMode.Callback = object : ActionMode.Callback {
                            override fun onCreateActionMode(
                                mode: ActionMode?,
                                menu: Menu?
                            ): Boolean {
                                val menuInflater = mode?.menuInflater
                                menuInflater?.inflate(R.menu.action_saved_news_activity, menu)
                                return true
                            }

                            override fun onPrepareActionMode(
                                mode: ActionMode?,
                                menu: Menu?
                            ): Boolean {
                                isEnable = true
                                clickedItem(holder)
                                mode?.title = String.format("$selectedItemSize Selected")
                                return true
                            }

                            override fun onActionItemClicked(
                                mode: ActionMode?,
                                item: MenuItem?
                            ): Boolean {
                                when (item?.itemId) {
                                    R.id.menu_delete -> {
                                        articlesToRemove.forEach {
                                            onMultiSelect?.invoke(articleList.indexOf(it))
                                            articleList.remove(it)
                                        }
                                        mode?.finish()
                                    }
                                    R.id.menu_select_all -> {
                                        isSelectAll = true
                                        articlesToRemove.addAll(articleList)
                                    }
                                    else -> return true
                                }
                                notifyDataSetChanged()
                                return true
                            }

                            override fun onDestroyActionMode(mode: ActionMode?) {
                                isEnable = false
                                isSelectAll = false
                                articlesToRemove.clear()
                                onHideToolbar?.invoke(false)
                                notifyDataSetChanged()
                            }
                        }
                        holder.itemView.startActionMode(callback)
                    } else {
                        clickedItem(holder)
                    }
                }
                return@setOnLongClickListener true
            }
            if (isSelectAll) {
                holder.checkBox.visibility = View.VISIBLE
            } else {
                holder.checkBox.visibility = View.GONE
            }
        }
    }

    private fun clickedItem(holder: ViewHolder) {
        val selectedArticle = articleList[holder.adapterPosition]
        if (holder.checkBox.visibility == View.GONE) {
            holder.checkBox.visibility = View.VISIBLE
            articlesToRemove.add(selectedArticle)
        } else {
            holder.checkBox.visibility = View.GONE
            articlesToRemove.remove(selectedArticle)
            selectedItemSize = articlesToRemove.size
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun onArticleCLicked(onClick: (ArticleDetails) -> Unit) {
        this.onClick = onClick
    }

    fun onMultiSelected(onMultiSelect: (Int) -> Unit) {
        this.onMultiSelect = onMultiSelect
    }

    fun onToolbarHidden(onHideToolbar: (Boolean) -> Unit){
        this.onHideToolbar = onHideToolbar
    }

    fun clear() {
        articleList.clear()
        notifyDataSetChanged()
    }

    fun removeArticle(position: Int) {
        articleList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateArticleList(articles: List<ArticleDetails>) {
        articleList.addAll(articles)
        notifyDataSetChanged()
    }

    fun undoArticleRemoved(position: Int, articleDetails: ArticleDetails) {
        articleList.add(position, articleDetails)
        notifyItemInserted(position)
    }
}


