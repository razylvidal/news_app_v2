package com.androidapp.newsclientappcleanarchitecture.data

import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.API_KEY
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.COUNTRY
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.domain.Category
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.LANGUAGE
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.PAGESIZE
import kotlinx.coroutines.*

class ArticleRepositoryImpl (
    private val remoteService: ArticleRemoteService
    ): ArticleRepository {

    override suspend fun fetchNewsArticles(category: String) = withContext(Dispatchers.IO) {
            if (category == "ALL") {
                remoteService.getAllNews(
                    API_KEY,
                    COUNTRY
                )
            } else {
                remoteService.getNewsByCategory(
                    COUNTRY,
                    category,
                    API_KEY
                )
            }.articles.map { it.toDomain() }.toMutableList()
        }

    override suspend fun searchNews(searchQuery: String): List<ArticleDetails> {
        return withContext(Dispatchers.IO){
            remoteService.getSearchNews(searchQuery, PAGESIZE, API_KEY,LANGUAGE)
        }.articles.map { it.toDomain() }.toList()
    }

    override fun fetchCategories(): List<String> = Category.values().map { it.name }
}