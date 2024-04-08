package com.example.demo.Util

import com.example.myapplication.model.NewsResponse


sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data: NewsResponse) : ApiState()
    object Empty : ApiState()
}
