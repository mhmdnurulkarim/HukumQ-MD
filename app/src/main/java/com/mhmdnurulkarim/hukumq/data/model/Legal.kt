package com.mhmdnurulkarim.hukumq.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Legal(
    val title: String,
    val link: String,
): Parcelable