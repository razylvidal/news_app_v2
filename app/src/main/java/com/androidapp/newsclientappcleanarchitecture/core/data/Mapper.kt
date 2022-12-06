package com.androidapp.newsclientappcleanarchitecture.core.data

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails

fun ArticleDetailsRaw.toDomain() = ArticleDetails(
    title = title,
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage =  urlToImage,
    url = url,
    content = content
)

