package com.androidapp.newsclientappcleanarchitecture.domain

import android.content.Context
import androidx.lifecycle.LiveData


interface ArticleRepository {
    suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails>
    suspend fun searchNews(searchQuery : String, pageSize : Int): List<ArticleDetails>
    fun fetchCategories(): List<String>
    fun insertNews(context: Context, news: ArticleDetails)
    fun deleteNews(context: Context, news: ArticleDetails)
    fun getAllNews(context: Context): LiveData<List<ArticleDetails>>

}