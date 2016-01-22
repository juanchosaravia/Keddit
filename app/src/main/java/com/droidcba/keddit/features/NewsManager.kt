package com.droidcba.keddit.features

import com.droidcba.carwapp.washer.api.RestAPI
import com.droidcba.keddit.commons.RedditNews
import com.droidcba.keddit.commons.RedditNewsItem
import rx.Observable

class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(after: String): Observable<RedditNews> {
        return Observable.create {
            subscriber ->
            val callResponse = api.getNews(after)
            val response = callResponse.execute()
            if (response.isSuccess) {
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