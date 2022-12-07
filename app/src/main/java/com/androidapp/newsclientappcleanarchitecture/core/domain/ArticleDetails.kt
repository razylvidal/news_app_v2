package com.androidapp.newsclientappcleanarchitecture.core.domain

data class ArticleDetails(
    val title: String?,
    val author: String?,
    val publishedAt: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val content: String?
)