package com.example.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Article
import com.example.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {
    fun saveFavoriteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addToFavorite(article)
        }
    }
}