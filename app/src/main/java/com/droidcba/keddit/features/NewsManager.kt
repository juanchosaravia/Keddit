package com.droidcba.keddit.features

import com.droidcba.keddit.api.NewsAPI
import com.droidcba.keddit.api.NewsRestAPI
import com.droidcba.keddit.commons.RedditNews
import com.droidcba.keddit.commons.RedditNewsItem
import rx.Observable

class NewsManager(private val api: NewsAPI = NewsRestAPI()) {

    fun getNews(after: String, limit: String = "20"): Observable<RedditNews> {
        return Observable.create {
            subscriber ->
            val callResponse = api.getNews(after, limit)
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
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

}