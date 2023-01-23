package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import android.app.AlertDialog
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

interface SavedNewsContract {
    interface View{
        fun showAlertDialog(alertDialog: AlertDialog, isVisible: Boolean)
        fun showSavedNews()
        fun showSnackBar(position: Int, selectedArticle: ArticleDetails)
    }
    interface Presenter{
        fun onSavedNewsViewReady(view: View)
        fun onSavedNewsViewDestroyed()
    }
}