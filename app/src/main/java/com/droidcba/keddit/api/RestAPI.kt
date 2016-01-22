package com.droidcba.carwapp.washer.api

import com.droidcba.keddit.api.RedditApi
import com.droidcba.keddit.api.RedditNewsResponse
import com.google.gson.GsonBuilder
import com.squareup.okhttp.OkHttpClient
import retrofit.Call
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory

class RestAPI() {

    private val redditApi: RedditApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClient())
                .build()

        redditApi = retrofit.create(RedditApi::class.java)
    }

    fun getNews(after: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after)
    }
}
