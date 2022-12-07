package com.androidapp.newsclientappcleanarchitecture.core.data

import com.google.gson.annotations.SerializedName

data class ArticleDetailsRaw (
    @SerializedName("title")
    var title: String?,
    @SerializedName("author")
    var author: String?,
    @SerializedName("publishedAt")
    var publishedAt: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("content")
    var content: String?
)