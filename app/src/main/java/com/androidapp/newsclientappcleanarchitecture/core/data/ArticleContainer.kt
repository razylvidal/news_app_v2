package com.androidapp.newsclientappcleanarchitecture.core.data

import com.google.gson.annotations.SerializedName

data class ArticleContainer (
    @SerializedName("articles")
    val articles: MutableList<ArticleDetailsRaw>
    )