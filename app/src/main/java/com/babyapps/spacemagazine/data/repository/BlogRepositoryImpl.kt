package com.babyapps.spacemagazine.data.repository

import androidx.room.withTransaction
import com.babyapps.spacemagazine.data.local.BlogDatabase
import com.babyapps.spacemagazine.data.remote.api.BlogApi
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.domain.mapper.BlogEntityMapper
import com.babyapps.spacemagazine.domain.model.Blog
import com.babyapps.spacemagazine.domain.repository.BlogRepository
import com.babyapps.spacemagazine.util.networnBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val api: BlogApi,
    private val db: BlogDatabase,
    private val mapper: BlogEntityMapper
) : BlogRepository {
    private val dao = db.dao()
    override fun getBlogs() = networnBoundResource(
        query = {
            dao.getAllBlogs()
        },
        fetch = {
            delay(2000L)
            api.getBlogs()
        },
        saveFetchResult = { blogs ->
            db.withTransaction {
                dao.deleteAllBlogs()
                dao.insertBlogs(blogs)
            }

        }
    )

    fun getFavoriteBlogs()=dao.getFavoriteBlogs()

    suspend fun insertIntoFavorites(blogDto: BlogDto) =
        dao.insertIntoFavorites(mapper.mapToEntity(blogDto))

    suspend fun deleteBlog(blog: Blog)=dao.deleteBlog(blog)

    suspend fun unDoBlog(blog: Blog)=dao.insertIntoFavorites(blog)

    fun isSaved(blogUrl: String) = dao.isSaved(blogUrl)


}