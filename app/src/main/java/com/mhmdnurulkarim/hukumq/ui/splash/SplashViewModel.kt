package com.mhmdnurulkarim.hukumq.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mhmdnurulkarim.hukumq.data.Repository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(private val repository: Repository): ViewModel() {
    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)
}