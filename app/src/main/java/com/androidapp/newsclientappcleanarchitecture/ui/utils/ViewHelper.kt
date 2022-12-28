package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.androidapp.newsclientappcleanarchitecture.R

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


