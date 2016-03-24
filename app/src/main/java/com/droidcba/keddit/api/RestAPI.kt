package com.droidcba.keddit.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI() {

    private val redditApi: RedditApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClient())
                .build()

        redditApi = retrofit.create(RedditApi::class.java)
    }

    fun getNews(after: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after)
    }
}
