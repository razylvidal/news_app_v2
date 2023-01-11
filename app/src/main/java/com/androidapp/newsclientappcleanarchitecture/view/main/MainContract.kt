package com.androidapp.newsclientappcleanarchitecture.view.main

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface MainContract {
    interface View{
        fun showProgressBar( isVisible: Boolean)
        fun showToast(message: String)
        //fun onClear()
        fun showNewsArticles(articleList: List<ArticleDetails>)
        fun showCategories(categoryList: List<String>)

    }
    interface Presenter{
        fun onMainViewReady(view: View)
        fun onMainViewDestroyed()
        fun onCategoryClicked(selectedCategory:String)
    }
}