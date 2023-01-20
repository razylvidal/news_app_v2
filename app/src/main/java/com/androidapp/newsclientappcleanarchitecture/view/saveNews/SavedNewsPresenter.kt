package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import android.content.Context
import androidx.lifecycle.LiveData
import com.androidapp.newsclientappcleanarchitecture.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetSavedArticlesUseCase
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.RemoveArticleUseCase
import javax.inject.Inject

class SavedNewsPresenter @Inject constructor(
    private val removeArticleUseCase: RemoveArticleUseCase,
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase
) : SavedNewsContract.Presenter{

    private var savedNewsView: SavedNewsContract.View? = null

    override fun onSavedNewsViewReady(view: SavedNewsContract.View) {
        this.savedNewsView = view
    }

    fun initializeDB(context: Context): SavedArticlesDatabase {
        return SavedArticlesDatabase.getDatabaseClient(context)
    }

    override fun onSavedNewsViewDestroyed() {
        savedNewsView = null
    }

    fun handleArticleToRemove(instanceOfDB: SavedArticlesDatabase, selectedArticle: ArticleDetails){
        removeArticleUseCase.removeNewsToDB(instanceOfDB, selectedArticle)
    }

    fun handleSavedArticlesFromDB(instanceOfDB: SavedArticlesDatabase): LiveData<List<ArticleDetails>> {
        return getSavedArticlesUseCase.fetchNewsFromDB(instanceOfDB)
    }
}