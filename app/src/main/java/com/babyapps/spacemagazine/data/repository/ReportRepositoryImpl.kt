package com.babyapps.spacemagazine.data.repository

import androidx.room.withTransaction
import com.babyapps.spacemagazine.data.local.ReportDatabase
import com.babyapps.spacemagazine.data.remote.api.ReportApi
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.domain.mapper.ReportEntityMapper
import com.babyapps.spacemagazine.domain.model.Report
import com.babyapps.spacemagazine.domain.repository.ReportRepository
import com.babyapps.spacemagazine.util.networnBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: ReportApi,
    private val db: ReportDatabase,
    private val mapper: ReportEntityMapper
) : ReportRepository {
    private val dao = db.dao()
    override fun getReports() = networnBoundResource(
        query = {
            dao.getAllReports()
        },
        fetch = {
            delay(2000L)
            api.getReports()
        },
        saveFetchResult = { reports ->
            db.withTransaction {
                dao.deleteAllReports()
                dao.insertReports(reports)
            }

        }
    )

    fun getFavoriteReports()=dao.getFavoriteReports()

    suspend fun insertIntoFavorites(reportDto: ReportDto) =
        dao.insertIntoFavorites(mapper.mapToEntity(reportDto))

    suspend fun deleteReport(report: Report)=dao.deleteReport(report)

    suspend fun unDoReport(report: Report)=dao.insertIntoFavorites(report)

    fun isSaved(reportUrl: String) = dao.isSaved(reportUrl)


}