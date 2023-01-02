package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

//function extension
fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun TextView.showText( format : String){
    text = format
}






