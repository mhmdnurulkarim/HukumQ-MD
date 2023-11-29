package com.mhmdnurulkarim.hukumq.di

import android.content.Context
import com.mhmdnurulkarim.hukumq.data.dataremote.NewsApiConfig
import com.mhmdnurulkarim.hukumq.data.model.NewsRepository

object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        val apiService = NewsApiConfig.getApiService()
//        val database = NewsDatabase.getInstance(context)
//        val dao = database.newsDao()
//        return NewsRepository.getInstance(apiService, dao)
        return NewsRepository.getInstance(apiService)
    }
}