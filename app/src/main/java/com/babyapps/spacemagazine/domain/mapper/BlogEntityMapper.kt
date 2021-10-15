package com.babyapps.spacemagazine.domain.mapper

import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.domain.model.Blog
import javax.inject.Inject

class BlogEntityMapper @Inject constructor() : EntityMapper<BlogDto, Blog> {
    override fun mapFromEntity(entity: Blog): BlogDto = BlogDto(
        id = entity.id,
        title = entity.title,
        summary = entity.summary,
        newsSite = entity.newsSite,
        url = entity.url,
        imageUrl = entity.imageUrl,
        publishedAt = entity.publishedAt,
    )

    override fun mapToEntity(dto: BlogDto): Blog = Blog(
        id = dto.id,
        title = dto.title,
        summary = dto.summary,
        newsSite = dto.newsSite,
        url = dto.url,
        imageUrl = dto.imageUrl,
        publishedAt = dto.publishedAt
    )
}