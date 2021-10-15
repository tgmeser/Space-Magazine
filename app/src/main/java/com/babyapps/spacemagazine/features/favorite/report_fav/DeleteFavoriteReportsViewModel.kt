package com.babyapps.spacemagazine.features.favorite.report_fav

import androidx.lifecycle.ViewModel
import com.babyapps.spacemagazine.data.local.ReportDao
import com.babyapps.spacemagazine.injection.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteFavoriteReportsViewModel @Inject constructor(
    private val dao: ReportDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        dao.deleteFavorites()
    }
}