package com.androidapp.newsclientappcleanarchitecture.ui.utils

import android.content.Context
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.ui.main.ReadFullNewsActivity

fun startReadFullNewsAct(context: Context, articleDetails: ArticleDetails){
    ReadFullNewsActivity.getIntent(context, articleDetails)
        .run(context::startActivity)
}