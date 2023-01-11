package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import android.app.AlertDialog

interface SavedNewsContract {
    interface View{
        fun showAlertDialog(alertDialog: AlertDialog, isVisible: Boolean)
        fun showSavedNews()
        fun showToast(message : String)
    }
    interface Presenter{
        fun onSavedNewsViewReady(view: View)
        fun onSavedNewsViewDestroyed()
    }
}