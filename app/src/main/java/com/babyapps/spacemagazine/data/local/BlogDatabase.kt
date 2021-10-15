package com.babyapps.spacemagazine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.domain.model.Blog

@Database(entities = [BlogDto::class, Blog::class],version = 1)
abstract class BlogDatabase : RoomDatabase(){
    abstract fun dao(): BlogDao
}