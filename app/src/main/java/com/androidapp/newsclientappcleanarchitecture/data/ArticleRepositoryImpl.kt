package com.androidapp.newsclientappcleanarchitecture.data

import com.androidapp.newsclientappcleanarchitecture.common.Constants
import com.androidapp.newsclientappcleanarchitecture.common.Constants.Companion.API_KEY
import com.androidapp.newsclientappcleanarchitecture.common.Constants.Companion.COUNTRY
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.domain.Category
import kotlinx.coroutines.*

class ArticleRepositoryImpl(
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


    override suspend fun searchNews(searchQuery: String) =
        withContext(Dispatchers.IO) {
            remoteService.searchForNews(API_KEY, searchQuery)
    }

    override fun fetchCategories(): List<String> = Category.values().map { it.name }






}