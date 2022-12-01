package com.androidapp.newsclientappcleanarchitecture.core.domain

import com.androidapp.newsclientappcleanarchitecture.core.data.SourcesRaw

data class ArticleDetails(
    val title: String?,
    val author: String?,
    val publishedAt: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val content: String?

)