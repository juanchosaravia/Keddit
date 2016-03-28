package com.droidcba.keddit.features

import com.droidcba.keddit.api.RestAPI
import com.droidcba.keddit.commons.RedditNews
import com.droidcba.keddit.commons.RedditNewsItem
import okhttp3.Cache
import rx.Observable

class NewsManager(val cache: Cache,
                  private val api: RestAPI = RestAPI(cache)) {

    fun getNews(after: String, limit: String = "6"): Observable<RedditNews> {
        return Observable.create {
            subscriber ->
            val callResponse = api.getNews(limit, after)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val news = response.body().data.children.map {
                    val item = it.data
                    RedditNewsItem(item.author, item.title, item.num_comments,
                            item.created, item.thumbnail, item.url)
                }
                subscriber.onNext(
                        RedditNews(
                                response.body().data.after ?: "",
                                response.body().data.before ?: "",
                                news))
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}