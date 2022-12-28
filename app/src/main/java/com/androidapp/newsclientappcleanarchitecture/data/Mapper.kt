package com.androidapp.newsclientappcleanarchitecture.data

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

fun ArticleDetailsRaw.toDomain() = ArticleDetails(
    title = title,
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage =  urlToImage,
    url = url,
    content = content
)


