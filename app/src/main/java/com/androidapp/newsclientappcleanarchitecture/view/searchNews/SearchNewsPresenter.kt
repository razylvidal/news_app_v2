package com.androidapp.newsclientappcleanarchitecture.view.searchNews

import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetSearchQueryUseCase
import com.androidapp.newsclientappcleanarchitecture.utils.Constants
import com.androidapp.newsclientappcleanarchitecture.utils.LogHelper
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchNewsPresenter @Inject constructor(
    private val articleResponse : GetArticlesUseCase,
    private val searchUseCase: GetSearchQueryUseCase
    ): SearchNewsContract.Presenter {

    private var searchNewsView: SearchNewsContract.View? = null
    private val scope = MainScope()

    override fun onSearchViewReady(view: SearchNewsContract.View) {
        this.searchNewsView = view
        setUpArticleView()
    }

    override fun onSearchViewDestroyed() {
        searchNewsView = null
    }

    private fun setUpArticleView(){
        searchNewsView?.showProgressBar(true)
        searchNewsView?.onClear()
        searchNewsView?.disableScroll()
        scope.launch {
            try {
                searchNewsView?.enableScroll()
                searchNewsView?.showProgressBar(false)
                val topHeadlines = articleResponse.getListOfArticles(Constants.DEFAULT_CATEGORY)
                searchNewsView?.showTopHeadlines(topHeadlines)
            }
            catch (exception: Exception){
                searchNewsView?.disableScroll()
                searchNewsView?.showProgressBar(false)
                searchNewsView?.showToast(exception.message?: "Something went wrong")
            }
        }
    }

    fun handleQueryArticleResponse(query : String, pageSize : Int){
        searchNewsView?.showProgressBar(true)
        searchNewsView?.onClear()
        scope.launch {
            try {
                delay(Constants.SEARCH_NEWS_TIME_DELAY)
                val queryResult = searchUseCase.getSearchResult(query, pageSize)
                searchNewsView?.showProgressBar(false)
                searchNewsView?.showQueryResult(queryResult)
                LogHelper.log("presenter",queryResult.size.toString())
            }
            catch (exception : Exception){
                LogHelper.log("reached limit",exception.toString())
                searchNewsView?.showProgressBar(false)
                searchNewsView?.showToast(exception.message?: "Something went wrong")
            }
        }
    }
}