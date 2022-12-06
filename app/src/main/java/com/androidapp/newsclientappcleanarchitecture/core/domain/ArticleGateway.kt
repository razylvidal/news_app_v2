package com.androidapp.newsclientappcleanarchitecture.core.domain

interface ArticleGateway {
    suspend fun fetchNewsArticles(category: String): MutableList<ArticleDetails>
    fun fetchCategories(): List<Category>
}