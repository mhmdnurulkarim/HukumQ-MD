package com.mhmdnurulkarim.hukumq.data.dataremote

import retrofit2.http.GET

interface NewsApiService {
    @GET("antara/hukum")
    suspend fun getNews(): NewsResponse
}