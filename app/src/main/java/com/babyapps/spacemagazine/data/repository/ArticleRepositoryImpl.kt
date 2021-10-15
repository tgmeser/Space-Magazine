package com.babyapps.spacemagazine.data.repository

import androidx.room.withTransaction
import com.babyapps.spacemagazine.data.local.ArticleDatabase
import com.babyapps.spacemagazine.data.remote.api.ArticleApi
import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.domain.mapper.ArticleEntityMapper
import com.babyapps.spacemagazine.domain.model.Article
import com.babyapps.spacemagazine.domain.repository.ArticleRepository
import com.babyapps.spacemagazine.util.networnBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApi,
    private val db: ArticleDatabase,
    private val mapper: ArticleEntityMapper
) : ArticleRepository {
    private val dao = db.dao()
    override fun getArticles() = networnBoundResource(
        query = {
            dao.getAllArticles()
        },
        fetch = {
            delay(2000L)
            api.getArticles()
        },
        saveFetchResult = { articles ->
            db.withTransaction {
                dao.deleteAllArticles()
                dao.insertArticles(articles)
            }

        }
    )

    fun getFavoriteArticles()=dao.getFavoriteArticles()

    suspend fun insertIntoFavorites(articleDto: ArticleDto) =
        dao.insertIntoFavorites(mapper.mapToEntity(articleDto))

    suspend fun deleteArticle(article: Article)=dao.deleteArticle(article)

    suspend fun unDoArticle(article: Article)=dao.insertIntoFavorites(article)


    fun isSaved(articleUrl: String) = dao.isSaved(articleUrl)


}