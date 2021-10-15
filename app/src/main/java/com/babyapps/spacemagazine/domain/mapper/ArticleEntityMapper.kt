package com.babyapps.spacemagazine.domain.mapper

import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.domain.model.Article
import javax.inject.Inject

class ArticleEntityMapper @Inject constructor() : EntityMapper<ArticleDto, Article> {
    override fun mapFromEntity(entity: Article): ArticleDto = ArticleDto(
        id = entity.id,
        title = entity.title,
        summary = entity.summary,
        newsSite = entity.newsSite,
        url = entity.url,
        imageUrl = entity.imageUrl,
        publishedAt = entity.publishedAt,
    )

    override fun mapToEntity(dto: ArticleDto): Article = Article(
        id = dto.id,
        title = dto.title,
        summary = dto.summary,
        newsSite = dto.newsSite,
        url = dto.url,
        imageUrl = dto.imageUrl,
        publishedAt = dto.publishedAt
    )
}