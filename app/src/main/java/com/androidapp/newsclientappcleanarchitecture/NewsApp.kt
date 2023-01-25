package com.androidapp.newsclientappcleanarchitecture

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.androidapp.newsclientappcleanarchitecture.data.datastore.DataStoreManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltAndroidApp
class NewsApp : Application(){

    private lateinit var uiDataStore : DataStoreManager
    companion object {
        private val scope = MainScope()
    }

    override fun onCreate() {
        super.onCreate()
        uiDataStore = DataStoreManager(this)
        checkUIMode()
    }

    private fun checkUIMode(){
        scope.launch {
            if(uiDataStore.getUIMode.first()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}