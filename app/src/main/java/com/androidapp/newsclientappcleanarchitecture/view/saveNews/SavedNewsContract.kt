package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface SavedNewsContract {
    interface View{
        fun showSavedNews()
        fun showSnackBar(position: Int, selectedArticle: ArticleDetails)
    }
    interface Presenter{
        fun onSavedNewsViewReady(view: View)
        fun onSavedNewsViewDestroyed()
    }
}