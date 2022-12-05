package com.androidapp.newsclientappcleanarchitecture.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivityMainBinding
import com.androidapp.newsclientappcleanarchitecture.di.AppContainer

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var newsRV: RecyclerView
    private lateinit var categoryRV: RecyclerView
    private lateinit var loadingPB: ProgressBar
    private  val categoryList = mutableListOf<String>()
    private val newsList =  mutableListOf<ArticleDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findReferenceView()
        setUpRecyclerView()
        val appContainer = AppContainer()
        presenter = appContainer.mainPresenterFactory.create()
        presenter.onViewReady(this)

    }
    private fun findReferenceView(){
        binding.apply {
            newsRV = idRVNews
            categoryRV = idRVCategories
            loadingPB = idPBLoading
        }
    }
    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(newsList , this@MainActivity)
        categoryAdapter = CategoryAdapter(categoryList)
        categoryRV.adapter = categoryAdapter
        newsRV.adapter = newsAdapter
        newsRV.layoutManager = LinearLayoutManager(this)

    }
   override fun onDestroy(){
        presenter.onViewDestroyed()
        super.onDestroy()
    }
    override fun showProgressBar(state: Boolean) {
       if(state){
           loadingPB.visibility = View.VISIBLE
       }
        else
           loadingPB.visibility = View.GONE
    }

    override fun showToast(message: String) {
        Toast.makeText(this@MainActivity,
            message, Toast.LENGTH_SHORT).show()
    }

    override fun onClear() {
        newsAdapter.articles.clear()
    }

    override fun showNewsArticles(articleList: List<ArticleDetails>) {
        showToast(articleList.size.toString())
        newsAdapter.updateArticleData(articleList)

    }
    override fun showCategories(categoryList: MutableList<String>) {
        categoryAdapter.updateCategoryData(categoryList)
    }

}

