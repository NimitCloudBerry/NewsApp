package com.example.myapplication.viewModel



import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.Util.ApiState

import com.example.myapplication.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    // LiveData to observe news articles

    private val postStateFlow: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)

    val _postStateFlow: StateFlow<ApiState> = postStateFlow


    fun fetchNewsArticles() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
         repository.newsArticles().catch { e->
                postStateFlow.value=ApiState.Failure(e)
             Log.d("error", "${ApiState.Failure(e)}")

            }.collect { data->
                postStateFlow.value=ApiState.Success(data)
            }
    }
}



