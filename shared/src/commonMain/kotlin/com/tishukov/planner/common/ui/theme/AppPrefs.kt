package com.tishukov.planner.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

data class AppPrefs(
    val firstDayIsMonday: Boolean = true,
)

val LocalAppPrefs = staticCompositionLocalOf { AppPrefs() }
val LocalAppColors = staticCompositionLocalOf { darkPalette }


@Composable
fun AppTheme(
    isThemeDark: Boolean,
    appPrefs: AppPrefs,
    content: @Composable () -> Unit,
) {

    val colors = if (isThemeDark) darkPalette else lightPalette

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppPrefs provides appPrefs,
    ) {
        content()
    }
}
