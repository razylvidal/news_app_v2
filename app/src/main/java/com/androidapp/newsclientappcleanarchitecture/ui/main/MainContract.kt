package com.androidapp.newsclientappcleanarchitecture.ui.main

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category

interface MainContract {
    interface View{
        fun showProgressBar( state: Boolean)
        fun showToast(message: String)
        fun onClear()
        fun showNewsArticles(articleList: List<ArticleDetails>)
        fun showCategories(categoryList: List<Category>)

    }
    interface Presenter{
        fun onViewReady(view: View)
        fun onViewDestroyed()
        fun onCategoryClick(position: Int)

    }
}