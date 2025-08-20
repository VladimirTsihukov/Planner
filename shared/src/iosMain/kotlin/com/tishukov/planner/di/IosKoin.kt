package com.tishukov.planner.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import com.tishukov.planner.db.AppDb
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val platformModule: Module = module {
    single<SqlDriver> { NativeSqliteDriver(AppDb.Schema, "AppDb") }
}

object IosKoin {
    fun initialize(
        userDefault: NSUserDefaults,
    ) = initKoin(
        appModule = module {
            single<Settings> { NSUserDefaultsSettings(userDefault) }
        }
    )
}
