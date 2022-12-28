package com.androidapp.newsclientappcleanarchitecture.data

import com.google.gson.annotations.SerializedName

data class ArticleContainer (
    @SerializedName("articles")
    val articles: MutableList<ArticleDetailsRaw>
    )