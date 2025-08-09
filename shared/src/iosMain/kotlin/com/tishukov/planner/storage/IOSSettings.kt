package com.tishukov.planner.storage

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class AppSettings actual constructor() {
    actual val settings: Settings = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
}
