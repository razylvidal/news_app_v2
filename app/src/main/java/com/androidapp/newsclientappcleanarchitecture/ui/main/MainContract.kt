package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails

interface MainContract {
    interface View{
        fun showProgressBar( state: Boolean)
        fun showToast(message: String)
        fun onClear()
        fun showNewsArticles(articleList: List<ArticleDetails>)
        fun showCategories(categoryList: MutableList<String>)

    }
    interface Presenter{
        fun onViewReady(view: View)
        fun onViewDestroyed()
        fun onCategoryClick(position: Int)
    }
}