package com.androidapp.newsclientappcleanarchitecture.domain

import retrofit2.Response

interface ArticleRepository {
    suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails>
    suspend fun searchNews(searchQuery : String) : Response<ArticleDetails>
    fun fetchCategories(): List<String>

}