package com.androidapp.newsclientappcleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.androidapp.newsclientappcleanarchitecture.data.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.API_KEY
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.COUNTRY
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.domain.Category
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.LANGUAGE
import com.androidapp.newsclientappcleanarchitecture.utils.toDomain
import com.androidapp.newsclientappcleanarchitecture.utils.toDatabase
import kotlinx.coroutines.*
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
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

    override suspend fun searchNews(searchQuery: String, pageSize : Int): List<ArticleDetails> {
        return withContext(Dispatchers.IO){
            remoteService.getSearchNews(searchQuery, pageSize , API_KEY, LANGUAGE)
        }.articles.map { it.toDomain() }.toList()
    }

    override fun fetchCategories(): List<String> = Category.values().map { it.name }

    override fun insertNews(instanceOfDB: SavedArticlesDatabase, news: ArticleDetails) {
        val articleToInsert = news.toDatabase()
        CoroutineScope(Dispatchers.IO).launch {
            instanceOfDB.getArticleDao().insertNews(articleToInsert)
        }
    }

    override fun deleteNews(instanceOfDB: SavedArticlesDatabase, news: ArticleDetails) {
        val articleToRemove = news.toDatabase()
        CoroutineScope(Dispatchers.IO).launch {
            instanceOfDB.getArticleDao().deleteNews(articleToRemove)
        }
    }

    override fun getAllNews(instanceOfDB: SavedArticlesDatabase): LiveData<List<ArticleDetails>> {
        val dataFromDB = instanceOfDB.getArticleDao().getNewsFromDatabase()
        val data = Transformations.map(dataFromDB){ entityList ->
            entityList.map {
                it.toDomain()
            }
        }
        return data
    }
}