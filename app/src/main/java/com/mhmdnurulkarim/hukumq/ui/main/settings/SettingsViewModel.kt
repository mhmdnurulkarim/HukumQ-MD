package com.mhmdnurulkarim.hukumq.ui.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mhmdnurulkarim.hukumq.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: Repository): ViewModel() {
    fun saveThemeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.saveThemeSetting(isDarkModeActive)
    }

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)
}