package com.babyapps.spacemagazine.domain.repository

import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.util.Resource
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getReports(): Flow<Resource<List<ReportDto>>>

}