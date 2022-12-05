package com.androidapp.newsclientappcleanarchitecture.core.data

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.core.domain.Category

fun ArticleDetailsRaw.toDomain() = ArticleDetails(
    title = title,
    author = author,
    publishedAt = publishedAt,
    description = description,
    urlToImage =  urlToImage,
    url = url,
    content = content
)

/*fun CategoryRaw.toDomain() = Category(
    listOfCategory = CategoryRaw.values().map { it.name } as MutableList<String>
)*/

