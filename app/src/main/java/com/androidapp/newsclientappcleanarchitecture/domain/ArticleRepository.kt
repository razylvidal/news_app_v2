package com.androidapp.newsclientappcleanarchitecture.domain


interface ArticleRepository {
    suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails>
    suspend fun searchNews(searchQuery : String ): List<ArticleDetails>
    fun fetchCategories(): List<String>

}