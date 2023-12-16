package com.mhmdnurulkarim.hukumq.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mhmdnurulkarim.hukumq.data.model.Chat
import com.mhmdnurulkarim.hukumq.data.model.Message
import com.mhmdnurulkarim.hukumq.data.model.User

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: Message)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Transaction
    @Query("SELECT * FROM message WHERE uid= :uid ORDER BY timestamp ASC")
    fun getMyChat(uid: String): LiveData<List<Chat>>

//    @Transaction
//    @Query("SELECT * FROM message WHERE uid= :uid ORDER BY timestamp DESC")
//    fun getMyChat(uid: String): DataSource.Factory<Int, Chat>

//    @Query("DELETE * FROM message ")
//    suspend fun deleteAll(uid: String)
}