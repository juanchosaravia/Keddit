package com.droidcba.keddit.features.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.droidcba.keddit.R
import com.droidcba.keddit.commons.adapter.ViewType
import com.droidcba.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.droidcba.keddit.commons.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item_loading)) {
    }
}