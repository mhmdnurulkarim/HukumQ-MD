package com.mhmdnurulkarim.hukumq.data.remote

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String?
)

data class Data(

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("posts")
	val posts: List<PostsItem>
)

data class PostsItem(

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("pubDate")
	val pubDate: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("thumbnail")
	val thumbnail: String
)
