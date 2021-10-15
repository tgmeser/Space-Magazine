package com.babyapps.spacemagazine.injection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.babyapps.spacemagazine.data.local.*
import com.babyapps.spacemagazine.data.remote.api.ArticleApi
import com.babyapps.spacemagazine.data.remote.api.BlogApi
import com.babyapps.spacemagazine.data.remote.api.ReportApi
import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.domain.mapper.ArticleEntityMapper
import com.babyapps.spacemagazine.domain.mapper.BlogEntityMapper
import com.babyapps.spacemagazine.domain.mapper.EntityMapper
import com.babyapps.spacemagazine.domain.mapper.ReportEntityMapper
import com.babyapps.spacemagazine.domain.model.Article
import com.babyapps.spacemagazine.domain.model.Blog
import com.babyapps.spacemagazine.domain.model.Report
import com.babyapps.spacemagazine.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // -----------------------ARTICLE INJECTION--------------------------
    @Provides
    @Singleton
    @Named("Articles")
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideArticlesApi(@Named("Articles") retrofit: Retrofit): ArticleApi =
        retrofit.create(ArticleApi::class.java)


    @Provides
    @Singleton
    fun provideDb( app: Application): ArticleDatabase = Room.databaseBuilder(
        app, ArticleDatabase::class.java, Constants.ARTICLE_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideArticleDao(database: ArticleDatabase): ArticleDao = database.dao()

    @Singleton
    @Provides
    fun provideCacheMapper(): EntityMapper<ArticleDto, Article> = ArticleEntityMapper()


    //-------------------------BLOG INJECTION------------------------------------


    @Provides
    @Singleton
    @Named("Blogs")
    fun provideBlogRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideBlogApi(@Named("Blogs") retrofit: Retrofit): BlogApi =
        retrofit.create(BlogApi::class.java)


    @Provides
    @Singleton
    fun provideBlogDb( app: Application): BlogDatabase = Room.databaseBuilder(
        app, BlogDatabase::class.java, Constants.BLOG_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideBlogDao(database: BlogDatabase): BlogDao = database.dao()

    @Singleton
    @Provides
    fun provideBlogCacheMapper(): EntityMapper<BlogDto, Blog> = BlogEntityMapper()


    //-------------------------REPORTS INJECTION----------------------------------

    @Provides
    @Singleton
    @Named("Reports")
    fun provideReportRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideReportApi(@Named("Reports") retrofit: Retrofit): ReportApi =
        retrofit.create(ReportApi::class.java)


    @Provides
    @Singleton
    fun provideReportDb(@ApplicationContext app: Context): ReportDatabase = Room.databaseBuilder(
        app, ReportDatabase::class.java, Constants.REPORT_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideReportDao(database: ReportDatabase): ReportDao = database.dao()

    @Singleton
    @Provides
    fun provideReportCacheMapper(): EntityMapper<ReportDto, Report> = ReportEntityMapper()


    //--------------------------APPLICATION SETUP-----------------------------------------
    @Provides
    fun provideApplication(@ApplicationContext app: Context): SpaceMagazineApplication =
        app as SpaceMagazineApplication

    @ApplicationScope
    @Provides
    fun ApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope