package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import androidx.lifecycle.LiveData
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class GetSavedArticlesUseCase @Inject constructor(private val repository: ArticleRepository) {
    private var newsData: LiveData<List<ArticleDetails>>? = null
    fun fetchNewsFromDB(): LiveData<List<ArticleDetails>> {
        newsData = repository.getAllNews()
        return newsData!!
    }
}