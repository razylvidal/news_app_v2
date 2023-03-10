package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import androidx.lifecycle.LiveData
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetSavedArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.InsertNewsUseCase
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.RemoveArticleUseCase
import javax.inject.Inject

class SavedNewsPresenter @Inject constructor(
    private val removeArticleUseCase: RemoveArticleUseCase,
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val addNewsUseCase: InsertNewsUseCase
) : SavedNewsContract.Presenter{

    private var savedNewsView: SavedNewsContract.View? = null

    override fun onSavedNewsViewReady(view: SavedNewsContract.View) {
        this.savedNewsView = view
    }
    fun handleArticleToInsert(selectedArticle: ArticleDetails){
        addNewsUseCase.addNewsToDB(selectedArticle)
    }

    override fun onSavedNewsViewDestroyed() {
        savedNewsView = null
    }

    fun handleArticleToRemove(selectedArticle: ArticleDetails){
        removeArticleUseCase.removeNewsToDB(selectedArticle)
    }

    fun handleSavedArticlesFromDB(): LiveData<List<ArticleDetails>> {
        return getSavedArticlesUseCase.fetchNewsFromDB()
    }

}