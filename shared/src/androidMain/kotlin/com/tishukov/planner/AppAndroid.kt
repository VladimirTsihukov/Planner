package com.tishukov.planner

import android.app.Application

class AppAndroid : Application() {

    companion object Companion {
        lateinit var instance: AppAndroid
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
