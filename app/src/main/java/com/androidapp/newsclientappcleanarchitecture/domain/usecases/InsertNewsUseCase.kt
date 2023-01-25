package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.data.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class InsertNewsUseCase @Inject constructor(private val repository: ArticleRepository) {
    fun addNewsToDB(instanceOfDB: SavedArticlesDatabase, news: ArticleDetails) {
        repository.insertNews(instanceOfDB, news)
    }
}