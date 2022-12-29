package com.androidapp.newsclientappcleanarchitecture.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.androidapp.newsclientappcleanarchitecture.data.toDomain
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.main.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleDBRepository {
    private var articleDatabase: SavedArticlesDatabase? = null

    private fun initializeDB(context: Context): SavedArticlesDatabase {
        return SavedArticlesDatabase.getDatabaseClient(context)
    }

    fun insertNews(context: Context, news: ArticleDetails) {
        articleDatabase = initializeDB(context)
        val articleToInsert = news.toDomain()
        CoroutineScope(Dispatchers.IO).launch {
            articleDatabase!!.getArticleDao().insertNews(articleToInsert)
        }
    }

    fun deleteNews(context: Context, news: ArticleDetails) {
        articleDatabase = initializeDB(context)
        val articleToRemove = news.toDomain()
        CoroutineScope(Dispatchers.IO).launch {
            articleDatabase!!.getArticleDao().deleteNews(articleToRemove)
        }
    }

    fun getAllNews(context: Context): LiveData<List<ArticleDetails>> {
        articleDatabase = initializeDB(context)
        val dataFromDB = articleDatabase!!.getArticleDao().getNewsFromDatabase()
        val data = Transformations.map(dataFromDB) { entityList ->
            entityList.map {
                it.toDomain()
            }
        }
        return data
    }

}