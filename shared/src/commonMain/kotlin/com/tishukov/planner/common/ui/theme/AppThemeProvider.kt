package com.tishukov.planner.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object AppThemeProvider {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val prefs: AppPrefs
        @Composable
        @ReadOnlyComposable
        get() = LocalAppPrefs.current
}
