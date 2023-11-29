package com.mhmdnurulkarim.hukumq.ui.laws

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LawsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Laws Fragment"
    }
    val text: LiveData<String> = _text
}