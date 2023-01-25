package com.androidapp.newsclientappcleanarchitecture.utils

import com.androidapp.newsclientappcleanarchitecture.data.ArticleDetailsRaw
import com.androidapp.newsclientappcleanarchitecture.data.SourceRaw
import com.androidapp.newsclientappcleanarchitecture.data.database.ArticleEntity
import com.androidapp.newsclientappcleanarchitecture.data.database.ArticleSource
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.Source

fun ArticleDetailsRaw.toDomain() = ArticleDetails(
    id = id,
    title = title,
    source = source.toDomain(),
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage =  urlToImage,
    url = url,
    content = content
)

fun ArticleEntity.toDomain() = ArticleDetails(
    id = id,
    title = title,
    source = source.toDomain(),
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage = urlToImage,
    url = url,
    content = content
)

fun ArticleDetails.toDatabase() = ArticleEntity(
    id = id,
    title = title,
    source = source.toDatabase(),
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage =  urlToImage,
    url = url,
    content = content
)

fun SourceRaw.toDomain() = Source(
    id = id,
    name = name
)

fun ArticleSource.toDomain() = Source(
    id = id,
    name = name
)

fun Source.toDatabase() = ArticleSource(
    id = id,
    name = name
)

