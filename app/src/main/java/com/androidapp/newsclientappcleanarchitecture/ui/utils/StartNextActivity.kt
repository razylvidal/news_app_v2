package com.androidapp.newsclientappcleanarchitecture.ui.utils

import android.content.Context
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.main.ReadFullNewsActivity
import com.androidapp.newsclientappcleanarchitecture.ui.main.SearchNewsActivity


fun startReadFullNewsAct(context: Context, articleDetails: ArticleDetails){
    ReadFullNewsActivity.getIntent(context, articleDetails)
        .run(context::startActivity)
}

fun startSearchNewsAct(context: Context){
    SearchNewsActivity.getIntent(context)
        .run(context::startActivity)
}