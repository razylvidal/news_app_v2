package com.androidapp.newsclientappcleanarchitecture.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.FetchingDataUseCase
import com.androidapp.newsclientappcleanarchitecture.ui.main.MainPresenter

class MainPresenterFactory(private val articleUseCase: FetchingDataUseCase) {
     fun create(): MainPresenter {
        return MainPresenter(articleUseCase)
    }
}