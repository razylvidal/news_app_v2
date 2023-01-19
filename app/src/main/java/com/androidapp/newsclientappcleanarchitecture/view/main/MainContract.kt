package com.androidapp.newsclientappcleanarchitecture.view.main

interface MainContract {
    interface View{
        fun showProgressBar( isVisible: Boolean)
        fun showToast(message: String)
        fun setUpViewPager(categoryList: List<String>)

    }
    interface Presenter{
        fun onMainViewReady(view: View)
        fun onMainViewDestroyed()

    }
}