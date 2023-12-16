package com.mhmdnurulkarim.hukumq.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mhmdnurulkarim.hukumq.data.Repository
import com.mhmdnurulkarim.hukumq.di.Injection
import com.mhmdnurulkarim.hukumq.ui.main.MainViewModel
import com.mhmdnurulkarim.hukumq.ui.main.home.HomeViewModel
import com.mhmdnurulkarim.hukumq.ui.main.laws.LawsViewModel
import com.mhmdnurulkarim.hukumq.ui.main.lawsDetail.DetailLawsViewModel
import com.mhmdnurulkarim.hukumq.ui.main.news.NewsViewModel
import com.mhmdnurulkarim.hukumq.ui.main.settings.SettingsViewModel
import com.mhmdnurulkarim.hukumq.ui.splash.SplashViewModel

class ViewModelFactory private constructor(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LawsViewModel::class.java)) {
            return LawsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailLawsViewModel::class.java)) {
            return DetailLawsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}