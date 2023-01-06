package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: ArticleRepository) {
    fun getListOfCategories() =
        repository.fetchCategories()
}