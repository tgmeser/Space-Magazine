package com.babyapps.spacemagazine.data.remote.api

import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import retrofit2.http.GET

interface ReportApi {
    @GET("reports?_limit=200")
    suspend fun getReports(): List<ReportDto>
}