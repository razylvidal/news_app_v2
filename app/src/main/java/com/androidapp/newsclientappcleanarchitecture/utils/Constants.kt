package com.androidapp.newsclientappcleanarchitecture.utils

import com.androidapp.newsclientappcleanarchitecture.data.database.ArticleEntity
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails


class Constants {
    companion object{
        //f62020f198eb4a98804986b4517a22a2
        //aacbed560bc0495fb66a35c90f0d0852
        //8036416e6f6147f0a2103fe2fe9dfefa
        const val API_KEY = "f62020f198eb4a98804986b4517a22a2"
        const val BASE_URL = "https://newsapi.org/"
        const val COUNTRY = "ph"
        const val PAGE_SIZE = 20
        const val SEARCH_NEWS_TIME_DELAY = 2000L
        const val DEFAULT_CATEGORY = "ALL"
        const val LANGUAGE = "en"
        const val TOP_HEADLINES_COUNT = 5
    }
}