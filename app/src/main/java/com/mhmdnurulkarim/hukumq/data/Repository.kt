package com.mhmdnurulkarim.hukumq.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingData
import com.mhmdnurulkarim.hukumq.data.database.MessageDao
import com.mhmdnurulkarim.hukumq.data.model.Chat
import com.mhmdnurulkarim.hukumq.data.model.Message
import com.mhmdnurulkarim.hukumq.data.remote.NewsApiService
import com.mhmdnurulkarim.hukumq.data.model.News
import com.mhmdnurulkarim.hukumq.data.model.User
import com.mhmdnurulkarim.hukumq.data.preferences.ThemeDataStore
import com.mhmdnurulkarim.hukumq.data.remote.ChatApiService
import com.mhmdnurulkarim.hukumq.data.remote.ChatResponse

class Repository(
    private val newsApiService: NewsApiService,
    private val chatApiService: ChatApiService,
    private val dataStore: ThemeDataStore,
    private val messageDao: MessageDao
) {
    //Home Page
    fun getMyChat(uid: String): LiveData<List<Chat>> = messageDao.getMyChat(uid)

//    @Suppress("DEPRECATION")
//    fun getMyChat(uid: String): LiveData<PagedList<Chat>> {
//        val chat = messageDao.getMyChat(uid)
//        val config = PagedList.Config.Builder()
//            .setPageSize(PAGE_SIZE)
//            .build()
//        return LivePagedListBuilder(chat, config).build()
//    }

    suspend fun insertUser(user: User) = messageDao.insertUser(user)
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)

    fun postChatBot(input: String): LiveData<Result<ChatResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = chatApiService.postChat(input)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    //News Page
    fun getHeadlineNews(): LiveData<Result<List<News>>> = liveData {
        emit(Result.Loading)
        try {
            val response = newsApiService.getNews()
            val articles = response.data.posts
            val newsList = articles.map { article ->
                News(
                    article.title,
                    article.link,
                    article.pubDate,
                    article.thumbnail
                )
            }
            emit(Result.Success(newsList))
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    //Settings Page
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        dataStore.saveThemeSetting(isDarkModeActive)

    fun getThemeSetting() = dataStore.getThemeSetting()

    companion object {

        const val PAGE_SIZE = 30

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            newsApiService: NewsApiService,
            chatApiService: ChatApiService,
            dataStore: ThemeDataStore,
            messageDao: MessageDao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(newsApiService, chatApiService, dataStore, messageDao)
            }.also { instance = it }
    }
}