package com.mhmdnurulkarim.hukumq.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hukum_tertulis")
data class Hukum(
    @PrimaryKey
    @ColumnInfo(name = "judul")
    val judul: String,

    @ColumnInfo(name = "isi")
    val isi: String
)