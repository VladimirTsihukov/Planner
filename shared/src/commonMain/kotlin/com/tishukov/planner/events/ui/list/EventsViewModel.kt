package com.tishukov.planner.events.ui.list

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.base.BaseViewState
import com.tishukov.planner.categories.CategoriesRepository
import com.tishukov.planner.categories.models.Category
import com.tishukov.planner.common.ui.calendar.model.CalendarDay
import com.tishukov.planner.common.ui.calendar.model.CalendarLabel
import com.tishukov.planner.events.EventsRepository
import com.tishukov.planner.events.model.SpendEvent
import com.tishukov.planner.events.model.SpendEventUI
import com.tishukov.planner.events.model.toCalendarLabel
import com.tishukov.planner.events.model.toUI
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class EventsViewModel(
    private val eventsRepository: EventsRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<BaseAction, EventsViewModel.State, Nothing>() {

    override fun initialState() = State.NONE

    override fun onAction(action: BaseAction) {
        TODO("Not yet implemented")
    }

    init {
        activate()
    }

    fun selectDay(day: CalendarDay) {
        updateState { copy(selectedDay = day) }
    }

    fun createEvent(newEvent: SpendEvent) {
        viewModelScope.launch { eventsRepository.create(newEvent) }
    }

    private fun activate() {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow()
        ) { events, categories ->
            val labels = mapEventsCategoriesToLabels(events, categories)
            updateState {
                copy(
                    events = events,
                    categories = categories,
                    calendarLabels = labels
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun mapEventsCategoriesToLabels(
        events: List<SpendEvent>,
        categories: List<Category>
    ): List<CalendarLabel> {
        return events.map { event ->
            val category = categories.firstOrNull {
                it.id == event.categoryId
            } ?: Category.getEmpty()
            event.toCalendarLabel(category)
        }
    }

    sealed class Action : BaseAction

    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
        val calendarLabels: List<CalendarLabel>
    ) : BaseViewState {

        val eventsByDay: List<SpendEventUI>
            get() = events.filter { it.date == selectedDay?.date }
                .map { spendEvent ->
                    spendEvent.toUI(
                        categories.firstOrNull { it.id == spendEvent.categoryId } ?: Category.getEmpty()
                    )
                }

        companion object {
            val NONE = State(
                selectedDay = null,
                events = emptyList(),
                categories = emptyList(),
                calendarLabels = emptyList()
            )
        }
    }
}







