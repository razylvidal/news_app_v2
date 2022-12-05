package com.androidapp.newsclientappcleanarchitecture.di

import com.androidapp.newsclientappcleanarchitecture.core.domain.ArticleGateway
import com.androidapp.newsclientappcleanarchitecture.ui.main.MainPresenter

class MainPresenterFactory( private val characterGateway: ArticleGateway) {
     fun create(): MainPresenter {
        return MainPresenter(characterGateway)
    }
}