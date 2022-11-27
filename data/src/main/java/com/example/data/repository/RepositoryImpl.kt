package com.example.data.repository

import com.example.data.api.NewsService
import com.example.data.db.ArticleDao
import com.example.data.model.Article
import com.example.data.model.NewsResponse
import retrofit2.Response

class RepositoryImpl(
    private val newsService: NewsService,
    private val articleDao: ArticleDao
    ) {
    suspend fun getNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return newsService.getHeadlines(countryCode, pageNumber)
    }
    suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse> {
        return newsService.getEverything(query, pageNumber)
    }
    fun getFavoriteArticles(): List<Article> {
        return articleDao.getAllArticles()
    }
    suspend fun addToFavorite(article: Article) {
        articleDao.insert(article)
    }
    suspend fun deleteAllFavorites() {
        articleDao.delete()
    }
}