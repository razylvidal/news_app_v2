package com.androidapp.newsclientappcleanarchitecture.di

import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetResponseUseCase
import com.androidapp.newsclientappcleanarchitecture.ui.main.MainPresenter

class MainPresenterFactory(private val articleUseCase: GetResponseUseCase) {
     fun create(): MainPresenter {
        return MainPresenter(articleUseCase)
    }
}