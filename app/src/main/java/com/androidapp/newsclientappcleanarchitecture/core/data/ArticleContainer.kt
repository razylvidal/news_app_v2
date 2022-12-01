package com.androidapp.newsclientappcleanarchitecture.core.data

import com.google.gson.annotations.SerializedName

data class ArticleContainer (
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResult: Int,
    @SerializedName("articles")
    val articles: MutableList<ArticleDetailsRaw>
    )