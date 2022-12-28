package com.androidapp.newsclientappcleanarchitecture.data

import com.androidapp.newsclientappcleanarchitecture.common.Constants
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticleRemoteService {
    @GET("v2/top-headlines")
    suspend fun getAllNews(@Query("apiKey") key: String,
                           @Query ("country") country:String = Constants.COUNTRY
    ): ArticleContainer

    @GET("v2/everything")
    suspend fun searchForNews(@Query("apiKey") key: String,
                           @Query ("q") searchQuery:String
    ): Response<ArticleDetails>

    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(@Query ("country") country:String = Constants.COUNTRY,
                                  @Query("category") currentCategory:String,
                                  @Query("apiKey") key: String
    ): ArticleContainer

}