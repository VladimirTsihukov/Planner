package com.tishukov.planner

import android.app.Application
import android.content.Context
import com.tishukov.planner.di.initKoin
import com.tishukov.planner.extensions.initLog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

class AppAndroid : Application() {

    companion object Companion {
        lateinit var instance: AppAndroid
    }

    override fun onCreate() {
        super.onCreate()
        initLog()
        initKoin(appModule = module {
            single<Context> { this@AppAndroid.applicationContext }
        })
        instance = this
    }
}
