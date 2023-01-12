package com.androidapp.newsclientappcleanarchitecture.database

import android.content.Context
import androidx.room.*

@Database(
    entities = [ArticleEntity::class],
    version = 2
)
@TypeConverters(SourceConverter::class)
abstract class SavedArticlesDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: SavedArticlesDatabase? = null
        fun getDatabaseClient(context: Context): SavedArticlesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context.applicationContext,
                        SavedArticlesDatabase::class.java, "article_database")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }
}