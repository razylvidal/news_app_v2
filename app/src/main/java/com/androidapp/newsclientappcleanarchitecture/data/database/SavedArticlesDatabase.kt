package com.androidapp.newsclientappcleanarchitecture.data.database

import android.content.Context
import androidx.room.*

@Database(
    entities = [ArticleEntity::class],
    version = 1,
)
@TypeConverters(SourceConverter::class)
abstract class SavedArticlesDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: SavedArticlesDatabase? = null

        fun getDatabaseClient(context: Context): SavedArticlesDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        SavedArticlesDatabase::class.java, "article_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}