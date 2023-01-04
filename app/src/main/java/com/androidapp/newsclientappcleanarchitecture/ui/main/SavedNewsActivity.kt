package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivitySavedNewsBinding
import com.androidapp.newsclientappcleanarchitecture.di.AppContainer
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.adapters.SavedNewsAdapter
import com.androidapp.newsclientappcleanarchitecture.ui.utils.startReadFullNewsAct

class SavedNewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var newsData: MutableList<ArticleDetails>
    private lateinit var adapter: SavedNewsAdapter
    private lateinit var binding: ActivitySavedNewsBinding
    private lateinit var presenter: MainPresenter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvSavedNews
        val toolbar = binding.tbSavedNews
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false)
        recyclerView.layoutManager = layoutManager
        newsData = mutableListOf()

        val appContainer = AppContainer()
        presenter = appContainer.mainPresenterFactory.create()

        adapter = SavedNewsAdapter(newsData)

        // fetch saved news from db
        presenter.handleSavedArticlesFromDB(applicationContext).observe(this) {
            newsData.clear()
            newsData.addAll(it)
            adapter.notifyDataSetChanged()
        }

        adapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@SavedNewsActivity, articleData)
        }
        adapter.onArticleLongCLicked { articleData ->
            removeArticleFromList(articleData)
        }
        recyclerView.adapter = adapter
    }

    private fun removeArticleFromList(position: Int) {
        // Delete saved news dialog
        recyclerView.findViewHolderForAdapterPosition(position)
            ?.itemView?.setBackgroundColor(Color.GRAY)

        val alertDialog = AlertDialog.Builder(
            this@SavedNewsActivity).apply {
            setMessage("Remove this News?")
            setTitle("Alert!")
            setCancelable(false)

            setPositiveButton("Yes") { _, _ ->
                this@SavedNewsActivity.let {
                    presenter.handleArticleToRemove(it, newsData[position])
                }
                adapter.notifyItemRemoved(position)
                Toast.makeText(
                    this@SavedNewsActivity,
                    "Article removed!",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            setNegativeButton("No") { _, _ ->
                recyclerView.findViewHolderForAdapterPosition(position)
                    ?.itemView?.setBackgroundColor(Color.TRANSPARENT)
            }
        }.create()
        alertDialog.show()
    }
}