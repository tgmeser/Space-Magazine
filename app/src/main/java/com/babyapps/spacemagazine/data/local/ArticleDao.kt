package com.babyapps.spacemagazine.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    //ArticleDto

    @Query("select * from articles")
    fun getAllArticles(): Flow<List<ArticleDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleDto>)

    @Query("delete from articles")
    suspend fun deleteAllArticles()


    //Article

    @Query("select * from fav_articles")
    fun getFavoriteArticles(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoFavorites(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("delete from fav_articles")
    suspend fun deleteFavorites()

    @Query("select * from fav_articles where url like :articleUrl ")
    fun isSaved(articleUrl: String): Flow<List<Article>>



}