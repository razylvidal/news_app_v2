package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface MainContract {
    interface View{
        fun showProgressBar( isVisible: Boolean)
        fun showToast(message: String)
        fun onClear()
        fun showNewsArticles(articleList: List<ArticleDetails>)
        fun showCategories(categoryList: List<String>)
        fun showSavedNews(savedArticles: List<ArticleDetails>)
    }
    interface Presenter{
        fun onViewReady(view: View)
        fun onViewDestroyed()
        fun onCategoryClicked(selectedCategory:String)
    }
}