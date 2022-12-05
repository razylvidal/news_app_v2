package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category

class MainPresenter(private val repo: ArticleGateway):MainContract.Presenter {
    private var view: MainContract.View? = null
    private val category: Category? = null
    override fun onViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView("All")
    }

    override fun onViewDestroyed() {
        view = null
    }

    override fun onCategoryClick(position: Int) {
        val category = category?.listOfCategory?.get(position).toString()
        setUpArticleView(category)
    }

    private fun setUpCategoryView(){
        val categories = repo.fetchCategories()
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