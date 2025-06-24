package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                delay(1000) // Simulación de carga
                val news = repository.getNews()
                _newsList.value = news
            } catch (e: Exception) {
                e.printStackTrace()
            }

            _isLoading.value = false
        }
    }

    fun deleteNews(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNews(id)
                fetchNews()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addNews(titulo: String, contenido: String, fecha: String) {
        viewModelScope.launch {
            try {
                val newId = (_newsList.value.maxOfOrNull { it.id } ?: 0) + 1
                val newNews = News(newId, titulo, contenido, fecha)
                repository.addNews(newNews)
                fetchNews()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateNews(news: News) {
        viewModelScope.launch {
            try {
                repository.updateNews(news)
                fetchNews()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
