package com.androidapp.newsclientappcleanarchitecture.di

import com.androidapp.newsclientappcleanarchitecture.data.ArticleRemoteService
import com.androidapp.newsclientappcleanarchitecture.data.ArticleRepositoryImpl
import com.androidapp.newsclientappcleanarchitecture.common.Constants
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.domain.usecase.FetchingDataUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val articleRemoteService = retrofit.create(ArticleRemoteService::class.java)
    private val articleGateway: ArticleRepository = ArticleRepositoryImpl(articleRemoteService)
    private val fetchingDataUseCase = FetchingDataUseCase(articleGateway)
    val mainPresenterFactory = MainPresenterFactory(fetchingDataUseCase)

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}