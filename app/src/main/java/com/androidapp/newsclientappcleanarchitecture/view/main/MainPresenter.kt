package com.androidapp.newsclientappcleanarchitecture.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.newsclientappcleanarchitecture.datastore.DataStoreManager
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetCategoriesUseCase
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.DEFAULT_CATEGORY
import kotlinx.coroutines.*
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase,
    application: Application,
) : MainContract.Presenter, AndroidViewModel(application) {

    private var view: MainContract.View? = null
    private val scope = MainScope()
    private var currentCategory = DEFAULT_CATEGORY
    private var categories : MutableList<String> = mutableListOf()

    private val uiDataStore = DataStoreManager(application)

    val uiMode = uiDataStore.getUIMode

    fun saveUIModeToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

    override fun onMainViewReady(view: MainContract.View) {
        this.view = view
        setUpCategories()
        //requestForArticles(currentCategory)
    }

    override fun onMainViewDestroyed() {
        view = null
    }

//    fun currentCategory(selectedCategory: String) {
//        currentCategory = selectedCategory
//        //makeArticleRequest(currentCategory)
//    }

    private fun setUpCategories() {
        categories.addAll(categoryUseCase.getListOfCategories())
        view?.showViewPager(categories)
    }

//    private fun makeArticleRequest(category: String) : List<ArticleDetails> {
//        var listOfArticles : List<ArticleDetails> = listOf()
//        LogHelper.log("iscalled?", "yes")
//        view?.showProgressBar(true)
//        scope.launch {
//            try {
//                listOfArticles = articlesUseCase.getListOfArticles(category)
//                LogHelper.log(category, listOfArticles.size.toString())
////                val list: ArrayList<ArticleDetails> = ArrayList()
////                list.addAll(listOfArticles)
////                view?.showNewsArticles(list)
//                view?.showProgressBar(false)
//            } catch (exception: Exception) {
//                LogHelper.log("error", exception.toString())
//                view?.showProgressBar(false)
//                view?.showToast(exception.message ?: "Something went wrong")
//            }
//        }
//        return listOfArticles
//    }

//    fun requestArticles(category: String){
//        makeArticleRequest(category)
//    }

    //for swipe refresh
//    fun fetchArticles()  {
//        makeArticleRequest(currentCategory)
//    }
}