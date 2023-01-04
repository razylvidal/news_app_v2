package com.androidapp.newsclientappcleanarchitecture.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: ArticleEntity)

    @Query("SELECT * FROM Article_Table")
    fun getNewsFromDatabase(): LiveData<List<ArticleEntity>>

    @Delete
    fun deleteNews(news: ArticleEntity)
}