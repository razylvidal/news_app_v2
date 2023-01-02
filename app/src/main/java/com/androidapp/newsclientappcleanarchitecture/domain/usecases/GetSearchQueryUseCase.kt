package com.androidapp.newsclientappcleanarchitecture.domain.usecases

import com.androidapp.newsclientappcleanarchitecture.domain.ArticleRepository
import javax.inject.Inject

class GetSearchQueryUseCase @Inject constructor( private val repository: ArticleRepository) {
}