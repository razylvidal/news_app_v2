package com.androidapp.newsclientappcleanarchitecture.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Article_Table")
data class ArticleEntity (
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val author: String?,
    val publishedAt: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val content: String?
)
