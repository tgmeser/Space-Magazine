package com.babyapps.spacemagazine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.domain.model.Article


@Database(entities = [ArticleDto::class,Article::class],version = 1)
abstract class ArticleDatabase : RoomDatabase(){
    abstract fun dao(): ArticleDao
}