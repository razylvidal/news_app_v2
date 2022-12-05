package com.androidapp.newsclientappcleanarchitecture.core.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticleRemoteService {
    @GET("v2/top-headlines")
    suspend fun getAllNews(@Query("apiKey") key: String = Constants.API_KEY,
                   @Query ("country") country:String = Constants.COUNTRY
    ): ArticleContainer

    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(@Query ("country") country:String = Constants.COUNTRY,
                          @Query("category") currentCategory:String,
                          @Query("apiKey") key: String = Constants.API_KEY
    ): ArticleContainer

}