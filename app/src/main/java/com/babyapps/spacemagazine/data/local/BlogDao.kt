package com.babyapps.spacemagazine.data.local

import androidx.room.*
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.domain.model.Blog
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {

    // BlogDto
    @Query("select * from blogs")
    fun getAllBlogs(): Flow<List<BlogDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogs(blogs: List<BlogDto>)

    @Query("delete from blogs")
    suspend fun deleteAllBlogs()


    // Blog

    @Query("select * from fav_blogs")
    fun getFavoriteBlogs(): Flow<List<Blog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoFavorites(blog: Blog)

    @Delete
    suspend fun deleteBlog(blog: Blog)

    @Query("delete from fav_blogs")
    suspend fun deleteFavorites()

    @Query("select * from fav_blogs where url like :blogUrl ")
    fun isSaved(blogUrl: String): Flow<List<Blog>>
}