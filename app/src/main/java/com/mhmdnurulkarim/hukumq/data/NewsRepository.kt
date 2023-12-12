package com.mhmdnurulkarim.hukumq.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mhmdnurulkarim.hukumq.data.remote.NewsApiService
import com.mhmdnurulkarim.hukumq.data.model.News

class NewsRepository(
    private val apiService: NewsApiService,
) {
    fun getHeadlineNews(): LiveData<Result<List<News>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews()
            val articles = response.data.posts
            val newsList = articles.map { article ->
                News(
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