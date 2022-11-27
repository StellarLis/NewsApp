package com.example.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Article
import com.example.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {
    val favouriteList = MutableLiveData<List<Article>>()

    fun setFavouriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            val list = repositoryImpl.getFavoriteArticles().reversed()
            favouriteList.postValue(list)
        }
    }
    fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.deleteAllFavorites()
        }
    }
}