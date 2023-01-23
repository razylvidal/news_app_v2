package com.androidapp.newsclientappcleanarchitecture.view.main.fragments

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface FragmentContract {
    interface View{
        fun showArticles(response: MutableList<ArticleDetails>)
        fun showShimmerLayout(isVisible : Boolean)
    }
    interface Presenter{
        fun onViewReady(view: View, category: String)
        fun onViewDestroyed()
    }
}