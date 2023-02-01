package com.androidapp.newsclientappcleanarchitecture.domain

import androidx.lifecycle.LiveData


interface ArticleRepository {
    suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails>
    suspend fun searchNews(searchQuery : String, pageSize : Int): List<ArticleDetails>
    fun fetchCategories(): List<String>
    fun insertNews(news: ArticleDetails)
    fun deleteNews(news: ArticleDetails)
    fun getAllNews(): LiveData<List<ArticleDetails>>

}