package com.androidapp.newsclientappcleanarchitecture.view.main.fragments

import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.utils.LogHelper
import kotlinx.coroutines.MainScope
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

    private fun makeArticleRequest(category: String) {
        scope.launch {
            try {
                val listOfArticles = articlesUseCase.getListOfArticles(category)
                view?.showArticles(listOfArticles)
//                view?.showProgressBar(false)
            } catch (exception: Exception) {
                LogHelper.log("error", exception.toString())
//                view?.showProgressBar(false)
//                view?.showToast(exception.message ?: "Something went wrong")
            }
        }
    }
}