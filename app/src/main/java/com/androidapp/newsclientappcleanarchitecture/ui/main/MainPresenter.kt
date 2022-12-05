package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainPresenter(private val repo: ArticleGateway):MainContract.Presenter {
    private var view: MainContract.View? = null
    private val scope = MainScope()
    override fun onViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView("All")
    }
    override fun onViewDestroyed() {
        view = null
    }
    override fun onCategoryClick(selectedCategory: String) {
        setUpArticleView(selectedCategory)
    }
    private fun setUpCategoryView(){
        val categories = repo.fetchCategories()
        view?.showCategories(categories)
    }
    private fun setUpArticleView(category: String){
        view?.showProgressBar(state = true)
        view?.onClear()
        scope.launch{
            try {
                val data = repo.fetchNewsArticles(category)
                view?.showNewsArticles(data)
                view?.showProgressBar(state = false)
            }
            catch (exception: Exception){
                view?.showProgressBar(state = false)
                view?.showToast(exception.message ?: "Something went wrong")
            }

        }

    }
}