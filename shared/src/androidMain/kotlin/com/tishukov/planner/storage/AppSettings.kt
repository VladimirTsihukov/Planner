package com.tishukov.planner.storage

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.tishukov.planner.AppAndroid

actual class AppSettings actual constructor() {
    companion object {
        private const val SETTINGS_NAME = "AppSettings"
    }

    actual val settings: Settings = SharedPreferencesSettings(
        delegate = AppAndroid.Companion.instance.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)
    )
}
