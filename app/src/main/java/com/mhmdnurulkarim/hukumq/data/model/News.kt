package com.mhmdnurulkarim.hukumq.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
data class News(
    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "link")
    val link: String,

    @field:ColumnInfo(name = "pubDate")
    val pubDate: String,

    @field:ColumnInfo(name = "thumbnail")
    val thumbnail: String
) : Parcelable
