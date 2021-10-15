package com.babyapps.spacemagazine.data.remote.api

import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import retrofit2.http.GET

interface ArticleApi {

    @GET("articles?_limit=200")
    suspend fun getArticles(): List<ArticleDto>
}