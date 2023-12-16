package com.mhmdnurulkarim.hukumq.di

import android.content.Context
import com.mhmdnurulkarim.hukumq.data.remote.ApiConfig
import com.mhmdnurulkarim.hukumq.data.Repository
import com.mhmdnurulkarim.hukumq.data.database.AppDatabase
import com.mhmdnurulkarim.hukumq.data.preferences.ThemeDataStore
import com.mhmdnurulkarim.hukumq.data.preferences.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val newsApiService = ApiConfig.getNewsApiService()
        val chatApiService = ApiConfig.getChatApiService()
        val datastore = ThemeDataStore.getInstance(context.dataStore)
        val database = AppDatabase.getInstance(context)
        val messageDao = database.messageDao()
        return Repository.getInstance(newsApiService, chatApiService, datastore, messageDao)
    }
}