package com.example.newsapp

import com.example.newsapp.api.NewsApiService
import com.example.newsapp.api.RetrofitClient

class NewsRepository {

    private val apiService: NewsApiService = RetrofitClient.api

    suspend fun getNews(): List<News> {
        return apiService.getAllNews()
    }

    suspend fun addNews(news: News) {
        apiService.addNews(news)
    }

    suspend fun updateNews(news: News) {
        apiService.updateNews(news.id, news)
    }

    suspend fun deleteNews(id: Int) {
        apiService.deleteNews(id)
    }
}
