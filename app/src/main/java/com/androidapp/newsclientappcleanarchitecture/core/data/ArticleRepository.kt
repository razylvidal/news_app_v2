package com.androidapp.newsclientappcleanarchitecture.core.data

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(
    private val remoteService: ArticleRemoteService
    ): ArticleGateway {

    override suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails> {
        val results: MutableList<ArticleDetails> = withContext(Dispatchers.IO) {
            if (category == "All") {
                remoteService.getAllNews(
                    Constants.API_KEY,
                    Constants.COUNTRY
                ).articles.map { it.toDomain() }
            } else {
                remoteService.getNewsByCategory(
                    Constants.COUNTRY,
                    category,
                    Constants.API_KEY
                ).articles.map { it.toDomain() }
            }.toMutableList()
        }
        return results
    }
    override fun fetchCategories(): List<Category> {
        return CategoryRaw.values().map {
            Category(it.name)
        }
    }

   }