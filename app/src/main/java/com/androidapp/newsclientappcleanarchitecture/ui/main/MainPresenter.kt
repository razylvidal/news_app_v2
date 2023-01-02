package com.androidapp.newsclientappcleanarchitecture.ui.main

import androidx.lifecycle.ViewModel
import com.androidapp.newsclientappcleanarchitecture.LogHelper
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.DEFAULT_CATEGORY
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.FetchingDataUseCase
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPresenter (
    private val fetchData: FetchingDataUseCase,
    ):
    MainContract.Presenter, ViewModel(){

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
                val data = fetchData.getListOfArticles(category)
                view?.showNewsArticles(data)
                view?.showProgressBar(false)
            }
            catch (exception: Exception){
                view?.showProgressBar( false)
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
    }

    fun handleSearchedArticleResponse(query : String) : List<ArticleDetails>{
        var queryResult = listOf<ArticleDetails>()
        scope.launch {
            try {
                delay(SEARCH_NEWS_TIME_DELAY)
                queryResult = fetchData.getSearchResult(query)
                SearchNewsActivity.searchResult = queryResult
                LogHelper.log("presenter",queryResult.size.toString())

            }
            catch (exception : Exception){
                LogHelper.log("reached limit",exception.toString())
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
        return queryResult
    }

}