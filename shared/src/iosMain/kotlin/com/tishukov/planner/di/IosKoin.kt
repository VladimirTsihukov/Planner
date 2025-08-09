package com.tishukov.planner.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val platformModule: Module = module {

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
