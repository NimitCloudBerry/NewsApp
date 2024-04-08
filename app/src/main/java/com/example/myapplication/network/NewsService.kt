package com.example.myapplication.network


import com.example.myapplication.Util.AppUrl
import com.example.myapplication.model.NewsResponse
import retrofit2.http.GET

interface NewsService {
    @GET(AppUrl.END_URL)
    suspend fun getNewsArticles(): NewsResponse
}