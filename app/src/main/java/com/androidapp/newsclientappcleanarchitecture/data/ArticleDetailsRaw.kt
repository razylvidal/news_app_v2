package com.androidapp.newsclientappcleanarchitecture.data

import com.google.gson.annotations.SerializedName


data class ArticleDetailsRaw(
    val id: Int? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("source")
    val source: SourceRaw,
    @SerializedName("author")
    val author: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("content")
    val content: String?
)