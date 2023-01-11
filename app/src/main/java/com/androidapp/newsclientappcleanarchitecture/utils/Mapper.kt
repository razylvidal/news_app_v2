package com.androidapp.newsclientappcleanarchitecture.data

import com.androidapp.newsclientappcleanarchitecture.database.ArticleEntity
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

fun ArticleEntity.toDomain() = ArticleDetails(
    title = title,
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage = urlToImage,
    url = url,
    content = content
)

fun ArticleDetails.toDomain() = ArticleEntity(
    title = title,
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage =  urlToImage,
    url = url,
    content = content
)


