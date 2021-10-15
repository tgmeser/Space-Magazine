package com.babyapps.spacemagazine.features.report

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.data.repository.ReportRepositoryImpl
import com.babyapps.spacemagazine.domain.model.Report
import com.babyapps.spacemagazine.injection.SpaceMagazineApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    val app: SpaceMagazineApplication,
    private val repository: ReportRepositoryImpl
) :
    AndroidViewModel(app) {

    val reports = repository.getReports().asLiveData()

    //Check DB is empty or not
    val emptyDb: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkDatabaseIsEmptyOrNot(reports: List<Report>) {
        emptyDb.value = reports.isEmpty()
    }

    fun insertIntoFavorites(reportDto: ReportDto) = viewModelScope.launch {
        repository.insertIntoFavorites(reportDto)
    }

    fun isSaved(reportUrl: String) =
        repository.isSaved(reportUrl).asLiveData()

    fun getFavoriteReports() = repository.getFavoriteReports().asLiveData()

    fun deleteReport(report: Report) = viewModelScope.launch {
        repository.deleteReport(report)
    }

    fun unDoReport(report: Report) = viewModelScope.launch {
        repository.unDoReport(report)
    }
}
