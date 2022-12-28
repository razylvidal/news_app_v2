package com.androidapp.newsclientappcleanarchitecture.di

import com.androidapp.newsclientappcleanarchitecture.domain.usecase.FetchingDataUseCase
import com.androidapp.newsclientappcleanarchitecture.ui.main.MainPresenter

class MainPresenterFactory( private val articleUseCase: FetchingDataUseCase) {
     fun create(): MainPresenter {
        return MainPresenter(articleUseCase)
    }
}