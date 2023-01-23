package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivitySavedNewsBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.LogHelper
import com.androidapp.newsclientappcleanarchitecture.view.adapters.CustomAdapter
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.main.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsActivity : AppCompatActivity(), SavedNewsContract.View {

    private lateinit var newsData: MutableList<ArticleDetails>
    private lateinit var adapter: CustomAdapter
    private lateinit var binding: ActivitySavedNewsBinding
    private lateinit var dbInstance : SavedArticlesDatabase

    @Inject
    lateinit var presenter: SavedNewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbSavedNews)
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.rvSavedNews.layoutManager = layoutManager

        dbInstance = presenter.initializeDB(this)

        newsData = mutableListOf()
        adapter = CustomAdapter(newsData)

        adapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@SavedNewsActivity, articleData)
        }

        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                LogHelper.log("////", viewHolder.adapterPosition.toString())
                val selectedArticle = newsData[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                presenter.handleArticleToRemove(dbInstance, selectedArticle)
                adapter.removeArticle(viewHolder.adapterPosition)
                showSnackBar(position, selectedArticle)
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvSavedNews)

        //use this for multiple delete
//        adapter.onArticleLongCLicked { articleData ->
//            removeMultipleArticles(articleData)
//        }
        binding.rvSavedNews.adapter = adapter
        showSavedNews()
    }

    private fun removeMultipleArticles(position: Int) {
        setDialogBackground(Color.GRAY, position)
        val alertDialog = AlertDialog.Builder(this@SavedNewsActivity).apply {
            setPositiveButton("Yes") { _, _ ->
                presenter.handleArticleToRemove(dbInstance, newsData[position])
                adapter.notifyItemRemoved(position)
            }
            setNegativeButton("No") { _, _ ->
                setDialogBackground(Color.TRANSPARENT, position)
            }
        }.create()
        showAlertDialog(alertDialog, true)
    }

    private fun setDialogBackground(color: Int, position: Int) {
        binding.rvSavedNews.findViewHolderForAdapterPosition(position)
            ?.itemView?.setBackgroundColor(color)
    }

    override fun showAlertDialog(alertDialog: AlertDialog, isVisible: Boolean) {
        if (isVisible) alertDialog.show(
            "Alert!",
            "Remove this news?",
            false
        )
        else alertDialog.dismiss()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showSavedNews() {
        presenter.handleSavedArticlesFromDB(dbInstance).observe(this) {
            newsData.clear()
            newsData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showSnackBar(position: Int, selectedArticle: ArticleDetails) {
        Snackbar.make(
            binding.rvSavedNews,
            "Article Removed!", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                adapter.undoArticleRemoved(position, selectedArticle)
                presenter.handleArticleToInsert(dbInstance, selectedArticle)
                }.show()
    }
}

