package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.data.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class RemoveArticleUseCase @Inject constructor(private val repository: ArticleRepository) {
    fun removeNewsToDB(instanceOfDB: SavedArticlesDatabase, news: ArticleDetails) {
        repository.deleteNews(instanceOfDB, news)
    }
}