package com.mhmdnurulkarim.hukumq.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

//@IgnoreExtraProperties
@Entity(tableName = "message")
data class Message(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    val uid: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String? = null,

    @ColumnInfo(name = "text")
    val text: String? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long? = null
)