package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.data.api.NewsService
import com.example.data.constance.Constance.BASE_URL
import com.example.data.db.ArticleDao
import com.example.data.db.ArticleDatabase
import com.example.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun logging() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logging())
        .build()
    @Provides
    @Singleton
    fun provideRetrofit(): NewsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(NewsService::class.java)
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ArticleDao {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "article_database"
        ).build().getArticleDao()
    }
    @Provides
    @Singleton
    fun provideRepositoryImpl(retrofit: NewsService, articleDao: ArticleDao): RepositoryImpl {
        return RepositoryImpl(retrofit, articleDao)
    }
}