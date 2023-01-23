package com.androidapp.newsclientappcleanarchitecture.view.main.fragments

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface FragmentContract {
    interface View{
        fun showArticles(response: MutableList<ArticleDetails>)
    }
    interface Presenter{
        fun onViewReady(view: View, category: String)
        fun onViewDestroyed()
    }
}