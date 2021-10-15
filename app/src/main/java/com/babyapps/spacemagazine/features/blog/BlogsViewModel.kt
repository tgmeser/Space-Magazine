package com.babyapps.spacemagazine.features.blog

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.data.repository.BlogRepositoryImpl
import com.babyapps.spacemagazine.domain.model.Blog
import com.babyapps.spacemagazine.injection.SpaceMagazineApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(
    val app: SpaceMagazineApplication,
    private val repository: BlogRepositoryImpl
) :
    AndroidViewModel(app) {
    val blogs = repository.getBlogs().asLiveData()

    //Check DB is empty or not
    val emptyDb: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkDatabaseIsEmptyOrNot(blogs: List<Blog>) {
        emptyDb.value = blogs.isEmpty()
    }

    fun insertIntoFavorites(blogDto: BlogDto) = viewModelScope.launch {
        repository.insertIntoFavorites(blogDto)
    }

    fun isSaved(blogUrl: String) =
        repository.isSaved(blogUrl).asLiveData()

    fun getFavoriteBlogs() = repository.getFavoriteBlogs().asLiveData()

    fun deleteBlog(blog: Blog) = viewModelScope.launch {
        repository.deleteBlog(blog)
    }

    fun unDoBlog(blog: Blog) = viewModelScope.launch {
        repository.unDoBlog(blog)
    }

}
