package com.mhmdnurulkarim.hukumq.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mhmdnurulkarim.hukumq.data.NewsRepository
import com.mhmdnurulkarim.hukumq.di.Injection
import com.mhmdnurulkarim.hukumq.ui.main.news.NewsViewModel

class ViewModelFactory private constructor(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
//        else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
//            return NewsDetailViewModel(newsRepository) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideNewsRepository(context))
            }.also { instance = it }
    }
}