package com.androidapp.newsclientappcleanarchitecture.view.readFullNews

import android.content.Context
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.InsertNewsUseCase
import javax.inject.Inject

class ReadFullNewsPresenter @Inject constructor(
    private val addNewsUseCase: InsertNewsUseCase,
) : ReadFullNewsContract.Presenter {

    private var readFullNewsView: ReadFullNewsContract.View? = null

    override fun onReadFullNewsViewReady(view: ReadFullNewsContract.View) {
        this.readFullNewsView = view
    }

    override fun onReadFullNewsViewDestroyed() {
        readFullNewsView = null
    }

    fun handleArticleToInsert(context: Context, selectedArticle: ArticleDetails){
        addNewsUseCase.addNewsToDB(context, selectedArticle)
    }


}