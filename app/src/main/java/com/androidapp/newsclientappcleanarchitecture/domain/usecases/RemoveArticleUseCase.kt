package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class RemoveArticleUseCase @Inject constructor(private val repository: ArticleRepository) {
    fun removeNewsToDB(news: ArticleDetails) {
        repository.deleteNews(news)
    }
}