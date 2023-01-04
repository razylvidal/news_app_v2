package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.view.View
import android.widget.TextView

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun TextView.showText( format : String){
    text = format
    visibility = TextView.VISIBLE
}

fun TextView.hideText(){
    visibility = TextView.GONE
}






