package com.androidapp.newsclientappcleanarchitecture.utils

import com.androidapp.newsclientappcleanarchitecture.database.ArticleEntity
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
        const val TOP_HEADLINES_COUNT = 7
//        val LIST = mutableListOf(ArticleDetails(
//            "Elon Musk says he will resign as Twitter CEO if he finds replacement - The Washington Post",
//            "Rachel Lerman",
//            "2022-12-21T04:15:35Z",
//            "Elon Musk said he would find a new Twitter CEO after a poll mandated he step down.",
//            "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/AE73OFT7WAI63BZY5VZBPXRHOU.jpg&w=1440",
//            "https://www.washingtonpost.com/technology/2022/12/20/elon-musk-resign-twitter-ceo/",
//            "Comment on this story\\r\\nElon Musk said he would step down from the CEO role at Twitter just as soon as he finds someone else foolish enough to take the job.\\r\\nStill, Musk plans to keep control of the s… [+2884 chars]"
//        )
//        )
    }
}