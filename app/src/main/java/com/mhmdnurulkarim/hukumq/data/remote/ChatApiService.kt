package com.mhmdnurulkarim.hukumq.data.remote

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ChatApiService {

    @FormUrlEncoded
    @POST("chat")
    suspend fun postChat(
        @Field("input") input: String
    ): ChatResponse
}