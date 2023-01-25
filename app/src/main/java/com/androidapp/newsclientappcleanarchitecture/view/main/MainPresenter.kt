package com.androidapp.newsclientappcleanarchitecture.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.newsclientappcleanarchitecture.data.datastore.DataStoreManager
import com.androidapp.newsclientappcleanarchitecture.domain.usecases.GetCategoriesUseCase
import kotlinx.coroutines.*
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase,
    application: Application,
) : MainContract.Presenter, AndroidViewModel(application) {

    private var view: MainContract.View? = null
    private var categories : MutableList<String> = mutableListOf()

    private val uiDataStore = DataStoreManager(application)

    val uiMode = uiDataStore.getUIMode

    fun saveUIModeToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

    override fun onMainViewReady(view: MainContract.View) {
        this.view = view
        setUpCategories()
    }

    override fun onMainViewDestroyed() {
        view = null
    }

    private fun setUpCategories() {
        categories.addAll(categoryUseCase.getListOfCategories())
        view?.showViewPager(categories)
    }
}