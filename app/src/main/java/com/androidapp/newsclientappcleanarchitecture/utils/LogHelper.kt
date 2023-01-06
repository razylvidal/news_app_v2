package com.androidapp.newsclientappcleanarchitecture.utils

import android.util.Log

object LogHelper {
    fun log(tag : String,message:String) {
        Log.e(tag, message)
    }
}