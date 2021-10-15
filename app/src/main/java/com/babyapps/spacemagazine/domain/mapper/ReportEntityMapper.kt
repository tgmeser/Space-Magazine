package com.babyapps.spacemagazine.domain.mapper

import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.domain.model.Report
import javax.inject.Inject

class ReportEntityMapper @Inject constructor() : EntityMapper<ReportDto, Report> {
    override fun mapFromEntity(entity: Report): ReportDto = ReportDto(
        id = entity.id,
        title = entity.title,
        summary = entity.summary,
        newsSite = entity.newsSite,
        url = entity.url,
        imageUrl = entity.imageUrl,
        publishedAt = entity.publishedAt,
    )

    override fun mapToEntity(dto: ReportDto): Report = Report(
        id = dto.id,
        title = dto.title,
        summary = dto.summary,
        newsSite = dto.newsSite,
        url = dto.url,
        imageUrl = dto.imageUrl,
        publishedAt = dto.publishedAt
    )
}