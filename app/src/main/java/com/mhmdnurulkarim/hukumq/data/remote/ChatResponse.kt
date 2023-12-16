package com.mhmdnurulkarim.hukumq.data.remote

import com.google.gson.annotations.SerializedName

data class ChatResponse(

	@field:SerializedName("response")
	val response: String,
)