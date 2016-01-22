package com.droidcba.keddit.api

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface RedditApi {

    @GET("/top.json?limit=10")
    fun getTop(@Query("after") after: String): Call<RedditNewsResponse>;

}

data class RedditNewsResponse(val data: RedditDataResponse)

data class RedditDataResponse(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
)

data class RedditChildrenResponse(val data: RedditNewsDataResponse)

data class RedditNewsDataResponse(
        val author: String,
        val title: String,
        val num_comments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
)