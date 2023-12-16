package com.mhmdnurulkarim.hukumq.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val userId: String,
    val name: String,
    val photoUrl: String,
)

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int?,
    val uid: String,
    val text: String,
    val timestamp: Long,

    @ColumnInfo(defaultValue = "false")
    val currentUser: Boolean? = false
)

data class Chat(
    @Embedded
    val message: Message,

    @Relation(
        parentColumn = "uid",
        entityColumn = "userId"
    )
    val user: User
)