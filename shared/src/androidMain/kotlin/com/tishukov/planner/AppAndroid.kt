package com.tishukov.planner

import android.app.Application
import android.content.Context
import com.tishukov.planner.di.initKoin
import org.koin.dsl.module

class AppAndroid : Application() {

    companion object Companion {
        lateinit var instance: AppAndroid
    }

    override fun onCreate() {
        super.onCreate()
        initKoin(appModule = module {
            single<Context> { this@AppAndroid.applicationContext }
        })
        instance = this
    }
}
