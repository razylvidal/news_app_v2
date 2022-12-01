package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category

class MainPresenter(private val repo: ArticleGateway):MainContract.Presenter {
    private var view: MainContract.View? = null

    override fun onViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView("all")
    }

    override fun onViewDestroyed() {
        view = null
    }

    override fun onCategoryClick(position: Int) {
        val category = Category.values()[position].toString()
        setUpArticleView(category)
    }

    private fun setUpCategoryView(){
        val categories = Category.values().toMutableList()
        view?.showCategories(categories)
    }

    private fun setUpArticleView(category: String){
        view?.showProgressBar(state = true)
        view?.onClear()
        try {
            val data = repo.fetchMockData()
            view?.showNewsArticles(data)
            view?.showProgressBar(state = false)
        }
        catch (exception: Exception){
            view?.showProgressBar(state = false)
            view?.showToast(exception.message ?: "Something went wrong")
        }

    }


}