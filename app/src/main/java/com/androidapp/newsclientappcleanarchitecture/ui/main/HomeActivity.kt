package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivityHomeBinding
import com.androidapp.newsclientappcleanarchitecture.di.AppContainer
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.adapters.CategoryAdapter
import com.androidapp.newsclientappcleanarchitecture.ui.adapters.NewsAdapter
import com.androidapp.newsclientappcleanarchitecture.ui.utils.getCurrentDate
import com.androidapp.newsclientappcleanarchitecture.ui.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.ui.utils.startSearchNewsAct
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var articleRV: RecyclerView
    private lateinit var categoryRV: RecyclerView
    private lateinit var loadingPB: ProgressBar
    private lateinit var currentDate: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var searchNewsFab : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findReferenceView()
        setSupportActionBar(toolbar)
        setUpRecyclerView()
        currentDate.showText(getCurrentDate())

        val appContainer = AppContainer()
        presenter = appContainer.mainPresenterFactory.create()
        presenter.onViewReady(this)
        searchNewsFab.setOnClickListener {
            startSearchNewsAct(this)
        }
    }

    private fun findReferenceView() {
        binding.apply {
            articleRV = rvArticleList
            categoryRV = rvCategories
            loadingPB = pbLoad
            toolbar = tbMainAct
            currentDate = tvCurrentDate
            searchNewsFab = fabSearchNews
        }
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(mutableListOf(), this@HomeActivity)
        newsAdapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@HomeActivity, articleData)
        }
        categoryAdapter = CategoryAdapter(mutableListOf()) { selectedCategory ->
            presenter.onCategoryClicked(selectedCategory)
        }
        categoryRV.adapter = categoryAdapter
        articleRV.adapter = newsAdapter
        articleRV.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_main_act, menu)
        /*lifecycleScope.launchWhenStarted {
            val isChecked = presenter.getUIMode.first()
            val uiMode = menu.findItem(R.id.action_change_theme)
            uiMode.isChecked = isChecked
            setUIMode(uiMode, isChecked)
        }*/
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when (item.itemId) {
            R.id.action_saved_news -> {
                intent = Intent(applicationContext, SavedNewsActivity::class.java)
                startActivity(intent)
            }
             R.id.action_change_theme -> {
                 item.isChecked = !item.isChecked
                 setUIMode(item, item.isChecked)
                 //invalidateOptionsMenu()
             }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }
    override fun showProgressBar(isVisible: Boolean) {
        if (isVisible) loadingPB.show() else loadingPB.hide()
    }
    override fun showToast(message: String) {
        Toast.makeText(this@HomeActivity,
            message, Toast.LENGTH_SHORT).show()
    }
    override fun onClear() {
        newsAdapter.clear()
    }
    override fun showNewsArticles(articleList: List<ArticleDetails>) {
        newsAdapter.updateArticleData(articleList)
    }
    override fun showCategories(categoryList: List<String>) {
        categoryAdapter.updateCategoryData(categoryList)
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            //viewModel.saveToDataStore(true)
            item.setIcon(R.drawable.ic_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            //viewModel.saveToDataStore(false)
            item.setIcon(R.drawable.ic_day)
        }
    }
}
