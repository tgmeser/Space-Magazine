package com.babyapps.spacemagazine.features.article

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.data.repository.ArticleRepositoryImpl
import com.babyapps.spacemagazine.domain.model.Article
import com.babyapps.spacemagazine.injection.SpaceMagazineApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    val app: SpaceMagazineApplication,
    private val repository: ArticleRepositoryImpl
) : AndroidViewModel(app) {

    val articles = repository.getArticles().asLiveData()

    //Check DB is empty or not
    val emptyDb: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkDatabaseIsEmptyOrNot(articles: List<Article>) {
        emptyDb.value = articles.isEmpty()
    }

    fun insertIntoFavorites(articleDto: ArticleDto) = viewModelScope.launch {
        repository.insertIntoFavorites(articleDto)
    }

    fun isSaved(articleUrl: String) =
        repository.isSaved(articleUrl).asLiveData()

    fun getFavoriteArticles() = repository.getFavoriteArticles().asLiveData()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun unDoArticle(article: Article) = viewModelScope.launch {
        repository.unDoArticle(article)
    }
    /*
    private val _articles: MutableLiveData<List<ArticleDto>> = MutableLiveData()
    val articles : LiveData<List<ArticleDto>>
        get() = _articles


    init {

        viewModelScope.launch {
            val articles = api.getArticles()
            delay(2000)
            _articles.value=articles
        }
    }

     */


}
