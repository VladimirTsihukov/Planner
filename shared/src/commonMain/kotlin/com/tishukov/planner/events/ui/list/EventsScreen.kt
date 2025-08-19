package com.tishukov.planner.events.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.tishukov.planner.common.ui.atoms.FAB
import com.tishukov.planner.common.ui.atoms.RootBox
import com.tishukov.planner.common.ui.calendar.compose.CalendarColors
import com.tishukov.planner.common.ui.calendar.compose.DatePickerView
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.di.DatePickerSingleQualifier
import com.tishukov.planner.di.getKoinInstance
import com.tishukov.planner.events.ui.create.compose.CreateEventView
import com.tishukov.planner.events.ui.list.components.SpendEventItem
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(
    viewModel: EventsViewModel,
) {

    val sheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()

    ModalBottomSheetLayout(
        sheetContent = {
            CreateEventView(
                isExpand = sheetState.currentValue == ModalBottomSheetValue.Expanded,
                selectedDay = state.selectedDay,
                viewModel = getKoinInstance()
            ) { newEvent ->
                viewModel.createEvent(newEvent)
                scope.launch { sheetState.hide() }
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        modifier = Modifier.zIndex(1f)
    ) {
        RootBox {
            Column {
                DatePickerView(
                    viewModel = getKoinInstance(DatePickerSingleQualifier),
                    colors = CalendarColors.default.copy(
                        colorSurface = AppThemeProvider.colors.surface,
                        colorOnSurface = AppThemeProvider.colors.onSurface,
                        colorAccent = AppThemeProvider.colors.accent
                    ),
                    firstDayIsMonday = AppThemeProvider.prefs.firstDayIsMonday,
                    labels = state.calendarLabels,
                    selectDayListener = viewModel::selectDay
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.eventsByDay) { iventUI ->
                        SpendEventItem(iventUI)
                    }
                }
            }

            FAB { scope.launch { sheetState.show() } }
        }
    }
}
