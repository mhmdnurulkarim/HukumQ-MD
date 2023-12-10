package com.mhmdnurulkarim.hukumq.data.remote

import retrofit2.http.GET

interface NewsApiService {
    @GET("antara/hukum")
    suspend fun getNews(): NewsResponse
}