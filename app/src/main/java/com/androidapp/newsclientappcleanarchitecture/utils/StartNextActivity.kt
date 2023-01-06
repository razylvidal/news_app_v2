package com.androidapp.newsclientappcleanarchitecture.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.view.readFullNews.ReadFullNewsActivity
import com.androidapp.newsclientappcleanarchitecture.view.searchNews.SearchNewsActivity


fun startReadFullNewsAct(context: Context, articleDetails: ArticleDetails){
    ReadFullNewsActivity.getIntent(context, articleDetails)
        .run(context::startActivity)
}

fun startSearchNewsAct(context: Context){
    SearchNewsActivity.getIntent(context)
        .run(context::startActivity)
}

fun startShareNewsAct(url : String, context: Context){
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT,
            "Hey, checkout this news : $url")
        type = "text/plain"

    }.run {
        context.startActivity(Intent.createChooser(this, "Share with :"))
    }
}

fun openInBrowser(url : String, context: Context){
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run {
        context.startActivity(this)
    }
}