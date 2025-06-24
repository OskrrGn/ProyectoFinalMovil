package com.example.newsapp.api

import com.example.newsapp.News
import retrofit2.http.*

interface NewsApiService {

    @GET("api.php")
    suspend fun getAllNews(): List<News>

    @POST("api.php")
    suspend fun addNews(@Body news: News)

    @PUT("api.php")
    suspend fun updateNews(@Query("id") id: Int, @Body news: News)

    @DELETE("api.php")
    suspend fun deleteNews(@Query("id") id: Int)
}
