package com.androidapp.newsclientappcleanarchitecture.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Article_Table",
    indices = [Index(value = ["url"], unique = true)]
    )
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id : Int? = null,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "source")
    val source: ArticleSource,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "content")
    val content: String?
)
