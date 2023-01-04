package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository


class GetResponseUseCase(
    private val articleRepo: ArticleRepository,
) {
    private var newsData: LiveData<List<ArticleDetails>>? = null
    //API Response
    suspend fun getListOfArticles(selectedCategory: String) =
        articleRepo.fetchNewsArticles(selectedCategory)

    suspend fun getSearchResult(searchQuery: String,pageSize : Int) =
        articleRepo.searchNews(searchQuery, pageSize)

    fun getListOfCategories() =
        articleRepo.fetchCategories()

    //DB
    fun addNewsToDB(context: Context, news: ArticleDetails) {
        articleRepo.insertNews(context, news)
    }

    fun removeNewsToDB(context: Context, news: ArticleDetails) {
        articleRepo.deleteNews(context, news)
    }

    fun fetchNewsFromDB(context: Context): LiveData<List<ArticleDetails>> {
        newsData = articleRepo.getAllNews(context)
        return newsData!!
    }

}


