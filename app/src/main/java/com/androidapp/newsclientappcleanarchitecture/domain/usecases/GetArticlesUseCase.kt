package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor( private val repository: ArticleRepository) {

    suspend fun getListOfArticles(selectedCategory: String) =
        repository.fetchNewsArticles(selectedCategory)
}