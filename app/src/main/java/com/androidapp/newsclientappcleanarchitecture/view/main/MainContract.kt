package com.androidapp.newsclientappcleanarchitecture.view.main

interface MainContract {
    interface View{
        fun showToast(message: String)
        fun showViewPager(categoryList: List<String>)

    }
    interface Presenter{
        fun onMainViewReady(view: View)
        fun onMainViewDestroyed()

    }
}