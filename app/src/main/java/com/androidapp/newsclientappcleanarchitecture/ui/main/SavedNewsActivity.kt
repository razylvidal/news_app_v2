package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R

class SavedNewsActivity: AppCompatActivity(){

    private lateinit var savedNewsRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)

        savedNewsRV = findViewById(R.id.savedNewsRV)
        val layoutManager = LinearLayoutManager(this)
        savedNewsRV.layoutManager = layoutManager

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)





    }
}