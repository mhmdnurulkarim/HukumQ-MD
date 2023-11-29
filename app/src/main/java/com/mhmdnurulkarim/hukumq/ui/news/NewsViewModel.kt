package com.mhmdnurulkarim.hukumq.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mhmdnurulkarim.hukumq.data.model.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    private val _text = MutableLiveData<String>().apply {
        value = "This is news Fragment"
    }
    val text: LiveData<String> = _text
}