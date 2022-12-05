package com.androidapp.newsclientappcleanarchitecture.di

import com.androidapp.newsclientappcleanarchitecture.core.data.ArticleRemoteService
import com.androidapp.newsclientappcleanarchitecture.core.data.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.core.data.Constants
import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val articleRemoteService = retrofit.create(ArticleRemoteService::class.java)
    private val articleGateway: ArticleGateway = ArticleRepository(articleRemoteService)

    val mainPresenterFactory = MainPresenterFactory(articleGateway)

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}