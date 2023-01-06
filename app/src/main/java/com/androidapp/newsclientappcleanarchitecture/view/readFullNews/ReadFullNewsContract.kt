package com.androidapp.newsclientappcleanarchitecture.view.readFullNews

import android.app.AlertDialog

interface ReadFullNewsContract {
    interface View{
        fun showAlertDialog( alertDialog: AlertDialog, isVisible: Boolean)
        fun showToast(message : String)

    }
    interface Presenter{
        fun onReadFullNewsViewReady(view:View)
        fun onReadFullNewsViewDestroyed()

    }
}