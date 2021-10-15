package com.babyapps.spacemagazine.data.local

import androidx.room.*
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.domain.model.Report
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {

    //ReportDto
    @Query("select * from reports")
    fun getAllReports(): Flow<List<ReportDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<ReportDto>)

    @Query("delete from reports")
    suspend fun deleteAllReports()


    //Report

    @Query("select * from fav_reports")
    fun getFavoriteReports(): Flow<List<Report>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoFavorites(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Query("delete from fav_reports")
    suspend fun deleteFavorites()

    @Query("select * from fav_reports where url like :reportUrl ")
    fun isSaved(reportUrl: String): Flow<List<Report>>
}