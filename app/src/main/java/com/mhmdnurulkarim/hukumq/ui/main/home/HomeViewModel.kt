package com.mhmdnurulkarim.hukumq.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.mhmdnurulkarim.hukumq.data.Repository
import com.mhmdnurulkarim.hukumq.data.model.Chat
import com.mhmdnurulkarim.hukumq.data.model.Message
import com.mhmdnurulkarim.hukumq.data.model.User
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    fun getMyChat(uid: String): LiveData<List<Chat>> = repository.getMyChat(uid)

    fun postChatBot(input: String) = repository.postChatBot(input)

//    fun getMyChat(uid: String): LiveData<PagedList<Chat>> = repository.getMyChat(uid)

    fun insertMessage(
        uid: String,
        text: String,
        timestamp: Long,
        currentUser: Boolean
    ) {
        val message = Message(
            uid = uid,
            text = text,
            timestamp = timestamp,
            currentUser = currentUser,
            messageId = null)
        viewModelScope.launch {
            repository.insertMessage(message)
        }
    }

    fun insertUser(
        uid: String,
        name: String,
        photoUrl: String
    ) {
        val user = User(
            userId = uid,
            name = name,
            photoUrl = photoUrl)
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }
}