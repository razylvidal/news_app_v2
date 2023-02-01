package com.androidapp.newsclientappcleanarchitecture.di

import android.content.Context
import com.androidapp.newsclientappcleanarchitecture.data.ArticleRemoteService
import com.androidapp.newsclientappcleanarchitecture.data.database.SavedArticlesDatabase
import com.androidapp.newsclientappcleanarchitecture.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService() : ArticleRemoteService{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabaseInstance(
        @ApplicationContext applicationContext: Context
    ) : SavedArticlesDatabase{
        return SavedArticlesDatabase.getDatabaseClient(applicationContext)
    }
}