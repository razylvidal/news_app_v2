package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainPresenter(private val repo: ArticleGateway):MainContract.Presenter {
    private var view: MainContract.View? = null
    private val scope = MainScope()

    companion object{
        const val DEFAULT_CATEGORY = "All"
    }

    override fun onViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView(DEFAULT_CATEGORY)

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
        view?.showProgressBar(isVisible  = true)
        view?.onClear()
        scope.launch{
            try {
                val data = repo.fetchNewsArticles(category)
                view?.showNewsArticles(data)
                view?.showProgressBar(isVisible = false)
            }
            catch (exception: Exception){
                view?.showProgressBar(isVisible = false)
                view?.showToast(exception.message ?: "Something went wrong")
            }

        }

    }
}