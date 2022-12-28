package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.common.Constants.Companion.DEFAULT_CATEGORY
import com.androidapp.newsclientappcleanarchitecture.domain.usecase.FetchingDataUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainPresenter(private val fetchData: FetchingDataUseCase): MainContract.Presenter {
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
        view?.showProgressBar(isVisible  = true)
        view?.onClear()
        scope.launch{
            try {
                val data = fetchData.getListOfArticles(category)
                view?.showNewsArticles(data)
                view?.showProgressBar(isVisible = false)
            }
            catch (exception: Exception){
                view?.showProgressBar(isVisible = false)
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
    }

}