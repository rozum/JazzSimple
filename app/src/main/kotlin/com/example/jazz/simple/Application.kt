package com.example.jazz.simple

import android.app.Application as BaseApplication

class Application : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: Application? = null
        val instance: Application
            get() = requireNotNull(_instance)
    }
}