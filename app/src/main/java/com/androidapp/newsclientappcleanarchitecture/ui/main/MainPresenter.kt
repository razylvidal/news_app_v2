package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.androidapp.newsclientappcleanarchitecture.LogHelper
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.DEFAULT_CATEGORY
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetResponseUseCase
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPresenter (
    private val fetchData: GetResponseUseCase,
    ): MainContract.Presenter, ViewModel(){

    private var view: MainContract.View? = null
    private val scope = MainScope()

    override fun onViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView(DEFAULT_CATEGORY)
    }
    override fun onViewDestroyed() {
        view = null
    }
    override fun onCategoryClicked(selectedCategory: String) {
        setUpArticleView(selectedCategory)
    }
    private fun setUpCategoryView(){
        val categories = fetchData.getListOfCategories()
        view?.showCategories(categories)
    }
    private fun setUpArticleView(category: String){
        view?.showProgressBar(true)
        view?.onClear()
        scope.launch{
            try {
                val listOfArticles = fetchData.getListOfArticles(category)
                view?.showNewsArticles(listOfArticles)
                view?.showProgressBar(false)

            }
            catch (exception: Exception){
                view?.showProgressBar( false)
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
    }

    fun handleTopHeadlineResponse(){
        scope.launch {
            try {
                SearchNewsActivity.SEARCHED_RESULT = fetchData.getListOfArticles(DEFAULT_CATEGORY)
            }
            catch (exception: Exception){
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
    }

    fun handleQueryArticleResponse(query : String, pageSize : Int) : List<ArticleDetails>{
        var queryResult = listOf<ArticleDetails>()
        scope.launch {
            try {
                delay(SEARCH_NEWS_TIME_DELAY)
                queryResult = fetchData.getSearchResult(query, pageSize)
                SearchNewsActivity.SEARCHED_RESULT = queryResult
                LogHelper.log("presenter",queryResult.size.toString())
            }
            catch (exception : Exception){
                LogHelper.log("reached limit",exception.toString())
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
        return queryResult
    }

    fun handleArticleToInsert(context: Context, selectedArticle: ArticleDetails){
        fetchData.addNewsToDB(context, selectedArticle)
    }

    fun handleArticleToRemove(context: Context, selectedArticle: ArticleDetails){
        fetchData.removeNewsToDB(context, selectedArticle)
    }

    fun handleSavedArticlesFromDB(context: Context): LiveData<List<ArticleDetails>>{
        return fetchData.fetchNewsFromDB(context)
    }
}