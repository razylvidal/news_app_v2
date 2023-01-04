package com.androidapp.newsclientappcleanarchitecture.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.androidapp.newsclientappcleanarchitecture.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.API_KEY
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.COUNTRY
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.domain.Category
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.LANGUAGE
import kotlinx.coroutines.*

class ArticleRepositoryImpl (
    private val remoteService: ArticleRemoteService
    ): ArticleRepository {

    private var articleDatabase: SavedArticlesDatabase? = null

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

    private fun initializeDB(context: Context): SavedArticlesDatabase {
        return SavedArticlesDatabase.getDatabaseClient(context)
    }
    override fun insertNews(context: Context, news: ArticleDetails) {
        articleDatabase = initializeDB(context)
        val articleToInsert = news.toDomain()
        CoroutineScope(Dispatchers.IO).launch {
            articleDatabase!!.getArticleDao().insertNews(articleToInsert)
        }
    }

    override fun deleteNews(context: Context, news: ArticleDetails) {
        articleDatabase = initializeDB(context)
        val articleToRemove = news.toDomain()
        CoroutineScope(Dispatchers.IO).launch {
            articleDatabase!!.getArticleDao().deleteNews(articleToRemove)
        }
    }

    override fun getAllNews(context: Context): LiveData<List<ArticleDetails>> {
        articleDatabase = initializeDB(context)
        val dataFromDB = articleDatabase!!.getArticleDao().getNewsFromDatabase()
        val data = Transformations.map(dataFromDB){ entityList ->
            entityList.map {
                it.toDomain()
            }
        }
        return data
    }
}