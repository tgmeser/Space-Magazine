package com.babyapps.spacemagazine.domain.repository

import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticles(): Flow<Resource<List<ArticleDto>>>

}