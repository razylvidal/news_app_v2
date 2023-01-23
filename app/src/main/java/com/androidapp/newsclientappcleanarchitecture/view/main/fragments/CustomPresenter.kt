package com.androidapp.newsclientappcleanarchitecture.view.main.fragments

import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.utils.LogHelper
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomPresenter @Inject constructor(private val articlesUseCase: GetArticlesUseCase)
    : FragmentContract.Presenter {

    private val scope = MainScope()
    private var view: FragmentContract.View? = null

    override fun onViewReady(view: FragmentContract.View, category: String ){
        this.view = view
        makeArticleRequest(category)
    }

    override fun onViewDestroyed() {
       view = null
    }

    fun makeArticleRequest(category: String) {
        scope.launch {
            delay(2000L)
            try {
                val listOfArticles = articlesUseCase.getListOfArticles(category)
                view?.showArticles(listOfArticles)
                view?.showShimmerLayout(false)
            } catch (exception: Exception) {
                LogHelper.log("error", exception.toString())
            }
        }
    }
}