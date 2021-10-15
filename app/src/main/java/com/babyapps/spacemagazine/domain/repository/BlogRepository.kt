package com.babyapps.spacemagazine.domain.repository

import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.util.Resource
import kotlinx.coroutines.flow.Flow

interface BlogRepository {
    fun getBlogs(): Flow<Resource<List<BlogDto>>>

}