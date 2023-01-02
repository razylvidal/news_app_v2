package com.androidapp.newsclientappcleanarchitecture

import android.util.Log

object LogHelper {
    fun log(tag : String,message:String) {
        Log.e(tag, message)
    }
}