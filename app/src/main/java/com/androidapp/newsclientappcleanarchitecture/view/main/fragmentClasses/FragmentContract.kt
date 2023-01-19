package com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface FragmentContract {
    interface View{
        fun showArticles(response: List<ArticleDetails>)
    }
    interface Presenter{
        fun onViewReady(view: View, category: String)
        fun onViewDestroyed()
    }
}