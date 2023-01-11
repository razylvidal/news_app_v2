package com.androidapp.newsclientappcleanarchitecture.view.searchNews

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface SearchNewsContract {
    interface View {
        fun enableScroll()
        fun disableScroll()
        //fun onClear()
        fun showProgressBar( isVisible: Boolean)
        fun showToast(message : String)
        fun showTopHeadlines(topHeadlines: List<ArticleDetails>)
        fun showQueryResult(queryResult: List<ArticleDetails>)
    }
    interface Presenter{
        fun onSearchViewReady(view: View)
        fun onSearchViewDestroyed()
    }
}