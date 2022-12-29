package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivityHomeBinding
import com.androidapp.newsclientappcleanarchitecture.di.AppContainer
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.utils.startReadFullNewsAct
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity: AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var articleRV: RecyclerView
    private lateinit var categoryRV: RecyclerView
    private lateinit var loadingPB: ProgressBar
    private lateinit var currentDate: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var changeTheme : FloatingActionButton
    private  val categoryList = mutableListOf<String>()
    private val newsList =  mutableListOf<ArticleDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NewsClientAppCleanArchitecture)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findReferenceView()
        setSupportActionBar(toolbar)
        setUpRecyclerView()
        showCurrentDate()

        val appContainer = AppContainer()
        presenter = appContainer.mainPresenterFactory.create()
        presenter.onViewReady(this)

    }
    private fun findReferenceView(){
        binding.apply {
            articleRV = rvArticleList
            categoryRV = rvCategories
            loadingPB = pbLoad
            toolbar = tbMainAct
            currentDate = tvCurrentDate
        }
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(newsList)
        newsAdapter.onArticleCLicked { articleData ->
           startReadFullNewsAct(this@HomeActivity,articleData)
        }
        categoryAdapter = CategoryAdapter(categoryList) { selectedCategory ->
            presenter.onCategoryClicked(selectedCategory)
        }
        categoryRV.adapter = categoryAdapter
        articleRV.adapter = newsAdapter
        articleRV.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_main_act, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_news -> {
                intent = Intent(applicationContext, SavedNewsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.search_news -> {
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy(){
        presenter.onViewDestroyed()
        super.onDestroy()
    }

    override fun showProgressBar(isVisible: Boolean) {
        if(isVisible) loadingPB.show() else loadingPB.hide()
    }

    override fun showToast(message: String) {
        Toast.makeText(this@HomeActivity,
            message, Toast.LENGTH_SHORT).show()
    }

    override fun onClear() {
        newsAdapter.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showNewsArticles(articleList: List<ArticleDetails>) {
        newsAdapter.updateArticleData(articleList)
        newsAdapter.notifyDataSetChanged()
    }
    override fun showCategories(categoryList: List<String>) {
        categoryAdapter.updateCategoryData(categoryList)
    }

    @SuppressLint("SimpleDateFormat")
    private fun showCurrentDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, MMMM d")
        val date = dateFormat.format(calendar.time)
        currentDate.showText(date)
    }

    /*private fun startReadFullNewsAct( articleDetails: ArticleDetails){
        ReadFullNewsActivity.getIntent(this@HomeActivity, articleDetails)
            .run(this::startActivity)
    }*/
}
