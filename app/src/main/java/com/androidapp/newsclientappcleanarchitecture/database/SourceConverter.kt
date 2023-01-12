package com.androidapp.newsclientappcleanarchitecture.database

import androidx.room.TypeConverter

class SourceConverter {

    @TypeConverter
    fun fromArticleSource(source: ArticleSource) : String? {
        return source.name
    }

    @TypeConverter
    fun toArticleSource(name: String) : ArticleSource{
        return ArticleSource(name, name)
    }
}