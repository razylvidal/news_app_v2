package com.androidapp.newsclientappcleanarchitecture.core.data

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleDetails
import com.google.gson.annotations.SerializedName

data class ArticleListRaw(
    @SerializedName("articles")
    val listArticles: MutableList<ArticleDetails>
)