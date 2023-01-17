package com.androidapp.newsclientappcleanarchitecture.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.newsclientappcleanarchitecture.NewsApp
import com.androidapp.newsclientappcleanarchitecture.datastore.DataStoreManager
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetCategoriesUseCase
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.DEFAULT_CATEGORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase,
    private val articlesUseCase: GetArticlesUseCase,
    application: Application
): MainContract.Presenter, AndroidViewModel(application){

    private var view: MainContract.View? = null
    private val scope = MainScope()
    var currentCategory = DEFAULT_CATEGORY

    private val uiDataStore = DataStoreManager(application)

    val uiMode = uiDataStore.getUIMode

    fun saveUIModeToDataStore(isNightMode: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

    override fun onMainViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView(currentCategory)
    }
    override fun onMainViewDestroyed() {
        view = null
    }
    override fun onCategoryClicked(selectedCategory: String) {
        currentCategory = selectedCategory
        setUpArticleView(currentCategory)
    }
    private fun setUpCategoryView(){
        val categories = categoryUseCase.getListOfCategories()
        view?.showCategories(categories)
    }
     fun setUpArticleView(category: String){
        view?.showProgressBar(true)
        scope.launch{
            try {
                val listOfArticles = articlesUseCase.getListOfArticles(category)
                val list: ArrayList<ArticleDetails> = ArrayList()
                list.addAll(listOfArticles)
                view?.showNewsArticles(list)
                view?.showProgressBar(false)
            }
            catch (exception: Exception){
                view?.showProgressBar( false)
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
    }

    fun fetchArticles() {
        setUpArticleView(currentCategory)
    }
}