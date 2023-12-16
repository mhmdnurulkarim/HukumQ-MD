package com.mhmdnurulkarim.hukumq.ui.main.laws

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mhmdnurulkarim.hukumq.data.Repository

class LawsViewModel(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Laws Fragment"
    }
    val text: LiveData<String> = _text
}