package com.example.domain.usecases

import com.example.domain.model.Article
import com.example.domain.model.NewsResponse
import com.example.domain.repository.Repository

class GetNewsUseCase(private val repository: Repository) {
    suspend fun execute(): List<Article> {
        return repository.getNews("ru", 1).articles
    }
}