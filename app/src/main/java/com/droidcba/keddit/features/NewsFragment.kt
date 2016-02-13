package com.droidcba.keddit.features

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidcba.keddit.R
import com.droidcba.keddit.commons.RedditNews
import com.droidcba.keddit.commons.adapter.NewsAdapter
import com.droidcba.keddit.commons.extensions.inflate
import kotlinx.android.synthetic.main.news_fragment.*
import rx.schedulers.Schedulers

class NewsFragment : Fragment() {

    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    private var redditNews: RedditNews? = null
    private val newsManager by lazy { NewsManager() }
    private val newsList by lazy {
        news_list.apply {
            setHasFixedSize(true); // use this setting to improve performance
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout

            addOnScrollListener(InfiniteScrollListener({ requestMoreNews() }, linearLayout))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.news_fragment, container, false)
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            (newsList.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestMoreNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (newsList.adapter as NewsAdapter).getNews()
        if (redditNews != null && news.size > 0) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }

    }

    private fun requestMoreNews() {
        newsManager
                .getNews(redditNews?.after ?: "")
                .subscribeOn(Schedulers.io())
                .subscribe (
                        { retrievedNews ->
                            redditNews = retrievedNews
                            (newsList.adapter as NewsAdapter).addNews(retrievedNews.news)
                        },
                        { e -> Snackbar.make(newsList, e.message ?: "", Snackbar.LENGTH_LONG).show() }
                )
    }

    private fun initAdapter() {
        if (newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
    }
}

