package com.androidapp.newsclientappcleanarchitecture.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivityHomeBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.*
import com.androidapp.newsclientappcleanarchitecture.view.adapters.ViewPagerAdapter
import com.androidapp.newsclientappcleanarchitecture.view.saveNews.SavedNewsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var presenter: MainPresenter
//    private lateinit var newsAdapter: NewsAdapter
//    private lateinit var categoryAdapter: CategoryAdapter
//    private lateinit var articleRV: RecyclerView
//    private lateinit var categoryRV: RecyclerView
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbMainAct)
        binding.tvCurrentDate.showText(getCurrentDate())
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.vpArticleView.adapter = adapter
        presenter.onMainViewReady(this)
        //viewPager.visibility = View.VISIBLE
        //setUpRecyclerView()
        binding.fabSearchNews.setOnClickListener {
            startSearchNewsAct(this)
        }
        //refreshArticleList()
    }
//    private fun setUpRecyclerView() {
//        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
//        binding.vpArticleView.adapter = adapter
//        binding.vpArticleView.visibility = View.VISIBLE
//        newsAdapter = NewsAdapter(mutableListOf())
//        newsAdapter.onArticleCLicked { selectedArticleData ->
//            startReadFullNewsAct(this@HomeActivity, selectedArticleData)
//        }
//        categoryAdapter = CategoryAdapter(mutableListOf()) { selectedCategory ->
//            presenter.onCategoryClicked(selectedCategory)
//        }
//        categoryRV.adapter = categoryAdapter
//        articleRV.adapter = newsAdapter
//        articleRV.layoutManager = LinearLayoutManager(this)
  //  }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_main_act, menu)
        lifecycleScope.launchWhenStarted {
            val isChecked = presenter.uiMode.first()
            val item = menu.findItem(R.id.action_change_theme)
            item.isChecked = isChecked
            setUIMode(item, isChecked)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_saved_news -> {
                intent = Intent(applicationContext, SavedNewsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_change_theme -> {
                if(!item.isChecked)
                    showToast("Dark theme enabled!")

                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.onMainViewDestroyed()
        super.onDestroy()
    }

    override fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            LogHelper.log("progressBar", "is showing")
//            viewPager.visibility = View.GONE
//            newsAdapter.clear()
            binding.pbLoad.show()
        } else {
//            viewPager.visibility = View.VISIBLE
            binding.pbLoad.hide()
        }
    }

    override fun showToast(message: String) {
        toast(this@HomeActivity, message)
    }

    override fun setUpViewPager(categoryList: List<String>) {
        binding.vpArticleView.visibility = View.VISIBLE
        TabLayoutMediator(binding.categoryTabs, binding.vpArticleView) { tab, position ->
            tab.text = categoryList[position]
//            presenter.requestArticles(categoryList[position])
        }.attach()
        adapter.listOfCategories = categoryList
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            item.setIcon(R.drawable.ic_night)
            presenter.saveUIModeToDataStore(true)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            item.setIcon(R.drawable.ic_day)
            presenter.saveUIModeToDataStore(false)
        }
    }
//    private fun refreshArticleList() {
//        swipeRefresh.setOnRefreshListener {
//            showProgressBar(true)
//            presenter.fetchArticles()
//            swipeRefresh.isRefreshing = false
//        }
//    }
}
