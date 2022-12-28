package com.androidapp.newsclientappcleanarchitecture.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleDetails(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val author: String?,
    val publishedAt: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val content: String?
)