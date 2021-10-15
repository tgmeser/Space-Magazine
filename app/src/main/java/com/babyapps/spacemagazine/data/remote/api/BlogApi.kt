package com.babyapps.spacemagazine.data.remote.api

import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import retrofit2.http.GET

interface BlogApi {
    @GET("blogs?_limit=200")
    suspend fun getBlogs(): List<BlogDto>
}