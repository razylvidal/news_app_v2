package com.androidapp.newsclientappcleanarchitecture.domain

import androidx.lifecycle.LiveData
import com.androidapp.newsclientappcleanarchitecture.database.SavedArticlesDatabase


interface ArticleRepository {
    suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails>
    suspend fun searchNews(searchQuery : String, pageSize : Int): List<ArticleDetails>
    fun fetchCategories(): List<String>
    fun insertNews(instanceOfDB: SavedArticlesDatabase, news: ArticleDetails)
    fun deleteNews(instanceOfDB: SavedArticlesDatabase, news: ArticleDetails)
    fun getAllNews(instanceOfDB: SavedArticlesDatabase): LiveData<List<ArticleDetails>>

}