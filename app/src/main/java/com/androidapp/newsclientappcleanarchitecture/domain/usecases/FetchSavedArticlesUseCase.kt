package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class FetchSavedArticlesUseCase @Inject constructor( private val repository: ArticleRepository) {
}