package com.androidapp.newsclientappcleanarchitecture.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleDetails(
    val title: String,
    val source: Source,
    val author: String?,
    val publishedAt: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val content: String?
): Parcelable