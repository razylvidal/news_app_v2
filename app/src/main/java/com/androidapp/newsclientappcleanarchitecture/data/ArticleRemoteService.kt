package com.androidapp.newsclientappcleanarchitecture.data

import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants.Companion.COUNTRY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticleRemoteService {
    @GET("v2/top-headlines")
    suspend fun getAllNews(
        @Query("apiKey") key: String,
        @Query("country") country: String = COUNTRY,
    ): ArticleContainer

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery: String,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String,
        @Query("language")
        language: String
    ): ArticleContainer

    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(
        @Query("country") country: String = COUNTRY,
        @Query("category") currentCategory: String,
        @Query("apiKey") key: String,
    ): ArticleContainer

}