package com.droidcba.keddit.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RedditApi {

    @GET("/top.json")
    fun getTop(@Query("limit") limit: String,
               @Query("after") after: String,
               @Header("fresh") fresh: String?): Call<RedditNewsResponse>;

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