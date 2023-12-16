package com.mhmdnurulkarim.hukumq.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhmdnurulkarim.hukumq.data.model.Hukum
import com.mhmdnurulkarim.hukumq.data.model.Message
import com.mhmdnurulkarim.hukumq.data.model.News
import com.mhmdnurulkarim.hukumq.data.model.User

@Database(entities = [Message::class, User::class, Hukum::class, News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "HukumQ_Database.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}