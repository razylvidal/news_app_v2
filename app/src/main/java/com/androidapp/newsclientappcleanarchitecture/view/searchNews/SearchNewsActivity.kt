package com.androidapp.newsclientappcleanarchitecture.view.searchNews

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivitySearchNewsBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.view.adapters.NewsAdapter
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.PAGE_SIZE
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.main.hide
import com.androidapp.newsclientappcleanarchitecture.view.main.show
import com.androidapp.newsclientappcleanarchitecture.view.main.toast
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchNewsActivity : AppCompatActivity(), SearchNewsContract.View {

    private lateinit var binding: ActivitySearchNewsBinding
    @Inject
    lateinit var presenter: SearchNewsPresenter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var articleRV: RecyclerView
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var loadingBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            articleRV = rvSearchActivity
            appBarLayout = apSearchActivity
            loadingBar = pbLoadingBar
        }

        setUpRecyclerView()
        presenter.onSearchViewReady(this)

        binding.svSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.svSearchView.clearFocus()
                if (query.length > 2) {
                    presenter.handleQueryArticleResponse(query, PAGE_SIZE)
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 2) {
                    presenter.handleQueryArticleResponse(newText, PAGE_SIZE)
                }
                return false
            }
        })
    }
    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(mutableListOf(), this@SearchNewsActivity)
        newsAdapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@SearchNewsActivity, articleData)
        }
        articleRV.adapter = newsAdapter
        articleRV.layoutManager = LinearLayoutManager(this)
    }

    override fun showTopHeadlines(topHeadlines: List<ArticleDetails>) {
        newsAdapter.updateArticleData(topHeadlines)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showQueryResult(queryResult: List<ArticleDetails>) {
        newsAdapter.updateArticleData(queryResult)
    }
    override fun showProgressBar(isVisible: Boolean) {
        if (isVisible) loadingBar.show() else loadingBar.hide()
    }

    override fun showToast(message: String) {
        toast(this@SearchNewsActivity, message)
    }
    override fun onClear() {
        newsAdapter.clear()
    }
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchNewsActivity::class.java)
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun disableScroll() {
        val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(
            object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return false
                }
            }
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun enableScroll() {
        appBarLayout.setOnTouchListener(null)
    }
}