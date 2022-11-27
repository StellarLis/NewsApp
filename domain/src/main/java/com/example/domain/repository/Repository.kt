package com.example.domain.repository

import com.example.domain.model.NewsResponse

interface Repository {
    suspend fun getNews(countryCode: String, pageNumber: Int): NewsResponse
}