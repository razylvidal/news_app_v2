package com.androidapp.newsclientappcleanarchitecture.view.main

import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetCategoriesUseCase
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.DEFAULT_CATEGORY
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase,
    private val articlesUseCase: GetArticlesUseCase
): MainContract.Presenter{

    private var view: MainContract.View? = null
    private val scope = MainScope()

    override fun onMainViewReady(view: MainContract.View) {
        this.view = view
        setUpCategoryView()
        setUpArticleView(DEFAULT_CATEGORY)
    }
    override fun onMainViewDestroyed() {
        view = null
    }
    override fun onCategoryClicked(selectedCategory: String) {
        setUpArticleView(selectedCategory)
    }
    private fun setUpCategoryView(){
        val categories = categoryUseCase.getListOfCategories()
        view?.showCategories(categories)
    }
    private fun setUpArticleView(category: String){
        view?.showProgressBar(true)
        view?.onClear()
        scope.launch{
            try {
                val listOfArticles = articlesUseCase.getListOfArticles(category)
                view?.showNewsArticles(listOfArticles)
                view?.showProgressBar(false)
            }
            catch (exception: Exception){
                view?.showProgressBar( false)
                view?.showToast(exception.message?: "Something went wrong")
            }
        }
    }
}