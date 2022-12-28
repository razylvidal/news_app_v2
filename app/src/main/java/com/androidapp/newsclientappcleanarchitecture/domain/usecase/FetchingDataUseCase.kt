package com.androidapp.newsclientappcleanarchitecture.domain.usecase

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository

class FetchingDataUseCase(
    private val repository: ArticleRepository
    ){

     suspend fun getListOfArticles(selectedCategory: String) =
         repository.fetchNewsArticles(selectedCategory)

    suspend fun getSearchResult(searchQuery: String) =
        repository.searchNews(searchQuery)

     fun getListOfCategories() =
         repository.fetchCategories()


}


