package com.nekretnineferit

import android.app.Application
import android.content.Context

class NekretnineFeritApp : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        private var mContext: Context? = null
        val context: Context?
            get() = mContext
    }
}