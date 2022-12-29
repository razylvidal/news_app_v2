package com.androidapp.newsclientappcleanarchitecture.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

class ArticleDBViewModel : ViewModel(){

    private var articleRepo = ArticleDBRepository()

    private var newsData: LiveData<List<ArticleDetails>>? = null

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