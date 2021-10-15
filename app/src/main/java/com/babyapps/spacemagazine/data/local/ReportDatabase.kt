package com.babyapps.spacemagazine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.domain.model.Report

@Database(entities = [ReportDto::class, Report::class],version = 1)
abstract class ReportDatabase : RoomDatabase(){
    abstract fun dao(): ReportDao
}