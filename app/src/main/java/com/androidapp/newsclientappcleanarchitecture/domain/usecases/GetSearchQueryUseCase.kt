package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class GetSearchQueryUseCase @Inject constructor( private val repository: ArticleRepository) {
    suspend fun getSearchResult(searchQuery: String,pageSize : Int) =
        repository.searchNews(searchQuery, pageSize)
}