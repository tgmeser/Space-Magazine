package com.babyapps.spacemagazine.domain.mapper

interface EntityMapper<Dto, Entity> {
    fun mapFromEntity(entity: Entity): Dto
    fun mapToEntity(dto: Dto): Entity
}