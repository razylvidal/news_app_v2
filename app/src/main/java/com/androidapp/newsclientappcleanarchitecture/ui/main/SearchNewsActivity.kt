package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.LogHelper
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivitySearchNewsBinding
import com.androidapp.newsclientappcleanarchitecture.di.AppContainer
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.adapters.NewsAdapter
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.DEFAULT_PAGESIZE
import com.androidapp.newsclientappcleanarchitecture.ui.utils.startReadFullNewsAct

class SearchNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchNewsBinding
    private lateinit var presenter: MainPresenter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var articleRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.tbSearchNewsAct
        articleRV = binding.rView
        setSupportActionBar(toolbar)
        setUpRecyclerView()
        val appContainer = AppContainer()
        presenter = appContainer.mainPresenterFactory.create()
        presenter.handleTopHeadlineResponse()
        showSearchResult()
        LogHelper.log("searchNewsActonStart", SEARCHED_RESULT.size.toString())
    }
    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(mutableListOf(), this@SearchNewsActivity)
        newsAdapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@SearchNewsActivity, articleData)
        }
        articleRV.adapter = newsAdapter
        articleRV.layoutManager = LinearLayoutManager(this)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_search_news, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val item = menu.findItem(R.id.action_search_news)
        val searchView = item.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search latest news"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    binding.loadingBar.show()
                    presenter.handleQueryArticleResponse(query, DEFAULT_PAGESIZE)
                    LogHelper.log("onsubmit", SEARCHED_RESULT.size.toString())
                    showSearchResult()
                }
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                if (query.length > 2) {
                    binding.loadingBar.show()
                    presenter.handleQueryArticleResponse(query, DEFAULT_PAGESIZE)
                    LogHelper.log("ontextchanged", SEARCHED_RESULT.size.toString())
                    showSearchResult()
                }
                return false
            }
        })
        item.icon.setVisible(false, false)
        return true
    }
    @SuppressLint("NotifyDataSetChanged")
    fun showSearchResult() {
        binding.loadingBar.hide()
        newsAdapter.clear()
        newsAdapter.updateArticleData(SEARCHED_RESULT)
        newsAdapter.notifyDataSetChanged()
    }
    companion object {
        var SEARCHED_RESULT: List<ArticleDetails> = ArrayList()
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchNewsActivity::class.java)
        }
    }
}