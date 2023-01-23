package com.androidapp.newsclientappcleanarchitecture.view.main

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun TextView.showText(format : String){
    text = format
    visibility = TextView.VISIBLE
}

fun TextView.hideText(){
    visibility = TextView.GONE
}

fun AlertDialog.show(title : String, message: String, isCancellable : Boolean){
    setTitle(title)
    setMessage(message)
    show()
    setCancelable(isCancellable)
}

fun toast(context: Context, message: String){
    Toast.makeText(context,
        message, Toast.LENGTH_SHORT).show()
}











