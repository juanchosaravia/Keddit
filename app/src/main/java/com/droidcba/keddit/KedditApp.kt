package com.droidcba.keddit

import android.app.Application
import android.content.Context

class KedditApp : Application() {

    companion object {
        lateinit var kedditContext : Context

        fun getContext(): Context {
            return kedditContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        kedditContext = applicationContext
    }
}