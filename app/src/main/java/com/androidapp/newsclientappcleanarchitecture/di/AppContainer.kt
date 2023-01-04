package com.androidapp.newsclientappcleanarchitecture.di

import com.androidapp.newsclientappcleanarchitecture.data.ArticleRemoteService
import com.androidapp.newsclientappcleanarchitecture.data.ArticleRepositoryImpl
import com.androidapp.newsclientappcleanarchitecture.ui.utils.Constants
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetResponseUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val articleRemoteService = retrofit.create(ArticleRemoteService::class.java)
    private val articleGateway: ArticleRepository = ArticleRepositoryImpl(articleRemoteService)
    private val getResponseUseCase = GetResponseUseCase(articleGateway)
    val mainPresenterFactory = MainPresenterFactory(getResponseUseCase)

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}