package com.mhmdnurulkarim.hukumq.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mhmdnurulkarim.hukumq.data.dataremote.NewsApiService

class NewsRepository(
    private val apiService: NewsApiService,
) {
    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews()
            val articles = response.data.posts
            val newsList = articles.map { article ->
                NewsEntity(
                    article.title,
                    article.link,
                    article.pubDate,
                    article.thumbnail
                )
            }
            emit(Result.Success(newsList))
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: NewsApiService
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService)
            }.also { instance = it }
    }
}