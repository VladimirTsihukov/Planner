package com.tishukov.planner.storage

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsManager(
    private val settings: Settings,
) {

    companion object {
        private const val THEME_KEY = "theme_key"
    }

    var themeIsDark: Boolean
        set(value) {
            _themeIsDarkFlow.update { value }
            settings.putBoolean(THEME_KEY, value)
        }
        get() = settings.getBoolean(THEME_KEY, false)

    private val _themeIsDarkFlow = MutableStateFlow(themeIsDark)
    val themeIsDarkFlow = _themeIsDarkFlow.asStateFlow()
}
