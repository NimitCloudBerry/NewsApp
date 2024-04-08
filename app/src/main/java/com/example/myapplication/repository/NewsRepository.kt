package com.example.myapplication.repository
import com.example.myapplication.model.NewsResponse
import com.example.myapplication.network.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {
    suspend fun newsArticles(): Flow<NewsResponse> = flow {
        emit(newsService.getNewsArticles())
    }.flowOn(Dispatchers.IO)
}
