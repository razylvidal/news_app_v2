package com.androidapp.newsclientappcleanarchitecture.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

@Dao
interface ArticleDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertNews(news: ArticleEntity)

        @Query("SELECT * FROM Article_Table")
        fun getNewsFromDatabase(): LiveData<List<ArticleEntity>>

        @Delete
        fun deleteNews(news: ArticleEntity)
    }

