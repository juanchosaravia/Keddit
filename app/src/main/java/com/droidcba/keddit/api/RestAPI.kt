package com.droidcba.keddit.api

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI(val cache: Cache) {

    private val redditApi: RedditApi
    private var useCache = false

    init {

        val cacheInterceptor = okhttp3.Interceptor {
            chain ->
            val originalRequest = chain.request();

            val requestBuilder = originalRequest.newBuilder()
            //if (originalRequest.header("fresh") != null) {
            if (useCache) {
                requestBuilder.cacheControl(CacheControl.FORCE_NETWORK)
            }
            useCache = !useCache

            // Response
            val cacheHeaderValue = if (true) {
                "public, max-age=5"
            } else {
                "public, only-if-cached, max-stale=2419200"
            }

            val response = chain.proceed(requestBuilder.build());
            response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build()
        }

        val okHttpClient = OkHttpClient.Builder()
                //.cache(cache)
                //.addNetworkInterceptor(cacheInterceptor)
                //.addInterceptor(cacheInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        redditApi = retrofit.create(RedditApi::class.java)
    }

    fun getNews(limit: String, after: String, forceRefresh: String? = null): Call<RedditNewsResponse> {
        return redditApi.getTop(limit, after, forceRefresh)
    }
}
