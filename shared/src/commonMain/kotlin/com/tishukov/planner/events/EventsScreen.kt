package com.tishukov.planner.events

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import com.tishukov.planner.common.ui.calendar.compose.CalendarColors
import com.tishukov.planner.common.ui.calendar.compose.DatePickerView
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.di.getKoinInstance

@Composable
fun BoxScope.EventsScreen() {
    DatePickerView(
        viewModel = getKoinInstance(),
        colors = CalendarColors.default.copy(
            colorSurface = AppThemeProvider.colors.surface,
            colorOnSurface = AppThemeProvider.colors.onSurface,
            colorAccent = AppThemeProvider.colors.accent,
        ),
        firstDayIsMonday = AppThemeProvider.prefs.firstDayIsMonday,
        labels = emptyList(),
        selectDayListener = { day ->

        }
    )
}
