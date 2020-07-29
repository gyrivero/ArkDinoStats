package com.example.arkdinostats

import android.app.Application
import android.content.Context

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}