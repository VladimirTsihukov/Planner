package com.tishukov.planner.common.ui.calendar

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseEvent
import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.base.BaseViewState
import com.tishukov.planner.common.ui.calendar.DatePickerViewModel.Event
import com.tishukov.planner.common.ui.calendar.DatePickerViewModel.State
import com.tishukov.planner.common.ui.calendar.DatePickerViewModel.Action
import com.tishukov.planner.common.ui.calendar.extensions.next
import com.tishukov.planner.common.ui.calendar.extensions.prev
import com.tishukov.planner.common.ui.calendar.model.CalendarDay
import com.tishukov.planner.common.ui.calendar.model.CalendarLabel
import com.tishukov.planner.common.ui.calendar.model.CalendarMonth
import com.tishukov.planner.common.ui.calendar.model.CalendarWeek
import com.tishukov.planner.extensions.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

class DatePickerViewModel : BaseViewModel<Action, State, Event>() {

    override fun initialState() = State.NONE

    override fun onAction(action: Action) {
        // No actions defined yet
    }

    fun selectDay(day: CalendarDay) {
        pushEvent(Event.SelectDay(day))
        updateState { copy(selectedDay = day) }
        updateWeeks()
    }

    fun prevMonth() {
        val prevMonth = uiState.value.calendarMonth.month.prev()
        var year = uiState.value.calendarMonth.year
        if (prevMonth == Month.DECEMBER) year -= 1
        updateMonthInState(year, prevMonth)
    }

    fun nextMonth() {
        val nextMonth = uiState.value.calendarMonth.month.next()
        var year = uiState.value.calendarMonth.year
        if (nextMonth == Month.JANUARY) year += 1
        updateMonthInState(year, nextMonth)
    }

    fun updateYear(year: Int) {
        if (year == uiState.value.calendarMonth.year) return
        val newLocalDate = LocalDate(year, uiState.value.calendarMonth.month, 1)
        val newCalendarMonth = CalendarMonth.fromDate(newLocalDate)
        updateState { copy(calendarMonth = newCalendarMonth) }
        updateWeeks()
    }


    fun updateLabels(labels: List<CalendarLabel>) {
        updateState { copy(labels = labels) }
        updateWeeks()
    }

    fun activate(firstDayIsMonday: Boolean) {
        updateState {
            copy(
                calendarMonth = CalendarMonth.fromDate(LocalDate.now()),
                firstDayIsMonday = firstDayIsMonday
            )
        }
        updateWeeks()
    }

    private fun updateMonthInState(year: Int, month: Month) {
        val calendarMonth = CalendarMonth(year, month)
        updateState { copy(calendarMonth = calendarMonth) }
        updateWeeks()
    }

    private fun updateWeeks() = viewModelScope.launch(Dispatchers.Default) {
        with(uiState.value) {
            val weeks = calendarMonth.getWeeks(firstDayIsMonday, selectedDay, labels)
            updateState { copy(weeks = weeks) }
        }
    }


    data class State(
        val weeks: List<CalendarWeek>,
        val selectedDay: CalendarDay?,
        val firstDayIsMonday: Boolean,
        val labels: List<CalendarLabel>,
        val calendarMonth: CalendarMonth,
    ) : BaseViewState {

        companion object {
            val NONE = State(
                weeks = CalendarWeek.PLACEHOLDER,
                selectedDay = null,
                firstDayIsMonday = false,
                labels = emptyList(),
                calendarMonth = CalendarMonth.fromDate(LocalDate.now())
            )
        }
    }

    sealed interface Event : BaseEvent {
        data class SelectDay(val day: CalendarDay) : Event
    }

    sealed class Action: BaseAction {

    }

}
