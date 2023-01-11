package com.androidapp.newsclientappcleanarchitecture.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(DataStoreManager.DATA_STORE_NAME)

class DataStoreManager(val context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveToDataStore(isNightMode: Boolean){
        dataStore.edit{ preferences ->
            preferences[DARK_MODE_KEY] = isNightMode
        }
    }

    val getUIMode : Flow<Boolean> = dataStore.data
        .map { preferences ->
            val uiMode = preferences[DARK_MODE_KEY] ?: false
            uiMode
        }

    companion object{
        const val DATA_STORE_NAME = "user_preferences"
        private val DARK_MODE_KEY = booleanPreferencesKey("ui_mode")
    }
}

