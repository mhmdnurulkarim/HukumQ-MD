package com.mhmdnurulkarim.hukumq.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mhmdnurulkarim.hukumq.data.Repository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: Repository): ViewModel() {
    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)
}