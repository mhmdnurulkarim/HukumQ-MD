package com.mhmdnurulkarim.hukumq.ui.main.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mhmdnurulkarim.hukumq.data.Repository

class NewsViewModel(private val repository: Repository) : ViewModel() {
    fun getHeadlineNews() = repository.getHeadlineNews()
}