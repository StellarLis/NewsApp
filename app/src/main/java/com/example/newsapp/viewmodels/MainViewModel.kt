package com.example.newsapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Article
import com.example.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val newsList = MutableLiveData<List<Article>>()

    fun setNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repositoryImpl.getNews("us", 1)
            } catch (e: IOException) {
                Log.d("MyLog", "IOException, you might not have internet connection")
                withContext(Dispatchers.Main) { Toast.makeText(context, "You have no internet connection", Toast.LENGTH_LONG).show() }
                return@launch
            } catch (e: HttpException) {
                Log.d("MyLog", "HttpException, unexpected response")
                withContext(Dispatchers.Main) { Toast.makeText(context, "Some error occured. Try to reopen the application.", Toast.LENGTH_LONG).show() }
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                newsList.postValue(response.body()!!.articles)
            } else {
                Log.d("MyLog", "Response is not successful :(")
                withContext(Dispatchers.Main) { Toast.makeText(context, "Some error occured. Try to reopen the application.", Toast.LENGTH_LONG).show() }
            }
        }
    }
}