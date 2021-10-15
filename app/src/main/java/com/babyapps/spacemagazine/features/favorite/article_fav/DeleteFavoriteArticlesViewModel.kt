package com.babyapps.spacemagazine.features.favorite.article_fav

import androidx.lifecycle.ViewModel
import com.babyapps.spacemagazine.data.local.ArticleDao
import com.babyapps.spacemagazine.injection.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteFavoriteArticlesViewModel @Inject constructor(
    private val dao: ArticleDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        dao.deleteFavorites()
    }
}