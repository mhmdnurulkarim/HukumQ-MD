package com.mhmdnurulkarim.hukumq.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//@Database(entities = , version = 1, exportSchema = false)

abstract class HukumQDatabase {
//abstract class HukumQDatabase: RoomDatabase() {

//    abstract fun hukumqDao(): HukumQDao
//
//    companion object {
//
//        @Volatile
//        private var instance: HukumQDatabase? = null
//
//        fun getInstance(context: Context): HukumQDatabase {
//            return synchronized(this){
//                instance ?: Room.databaseBuilder(context, HukumQDatabase::class.java, "courses.db")
//                    .build()
//            }
//        }
//
//    }
}