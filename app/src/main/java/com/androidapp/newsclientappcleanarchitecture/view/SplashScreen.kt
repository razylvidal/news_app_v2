package com.androidapp.newsclientappcleanarchitecture.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.lifecycle.lifecycleScope
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.datastore.DataStoreManager
import com.androidapp.newsclientappcleanarchitecture.datastore.dataStore
import com.androidapp.newsclientappcleanarchitecture.view.main.HomeActivity
import com.androidapp.newsclientappcleanarchitecture.view.main.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    companion object{
        private val scope = MainScope()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val homeIntent = Intent(this, HomeActivity::class.java)
        scope.launch {
            delay(2000L)
            finish()
            startActivity(homeIntent)
        }
    }
}