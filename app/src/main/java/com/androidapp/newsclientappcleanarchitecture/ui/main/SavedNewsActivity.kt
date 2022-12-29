package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.data.database.ArticleDBViewModel
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.utils.startReadFullNewsAct

class SavedNewsActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArticleDBViewModel
    private lateinit var newsData: MutableList<ArticleDetails>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)
        recyclerView = findViewById(R.id.rv_saved_news)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        newsData = mutableListOf()

        val toolbar: Toolbar = findViewById(R.id.tb_saved_news)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[ArticleDBViewModel::class.java]
        val adapter = SavedNewsAdapter(newsData)
        adapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@SavedNewsActivity,articleData)
        }

        // fetch saved news from db
        viewModel.fetchNewsFromDB(context = applicationContext).observe(this) {
            newsData.clear()
            newsData.addAll(it)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnItemLongClickListener(object : SavedNewsAdapter.OnItemLongClickListener {
            @SuppressLint("PrivateResource")
            override fun onItemLongClick(position: Int) {
                // Delete saved news dialog
                recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.setBackgroundColor(
                    getThemeColor(com.google.android.material.R.attr.colorOnContainer)
                )

                val alertDialog = AlertDialog.Builder(this@SavedNewsActivity).apply {
                    setMessage("Delete this News?")
                    setTitle("Alert!")
                    setCancelable(false)

                    setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        this@SavedNewsActivity.let {
                            viewModel.removeNewsToDB(it, newsData[position])
                        }
                        adapter.notifyItemRemoved(position)
                        Toast.makeText(this@SavedNewsActivity, "Deleted!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    setNegativeButton("No") { _, _ ->
                        recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.setBackgroundColor(
                            getThemeColor(com.google.android.material.R.attr.colorPrimary)
                        )
                    }
                }.create()

                alertDialog.show()
            }
        })

        recyclerView.adapter = adapter

    }

    @ColorInt
    fun Context.getThemeColor(@AttrRes attribute: Int) = TypedValue().let {
        theme.resolveAttribute(attribute, it, true)
        it.data
    }


}