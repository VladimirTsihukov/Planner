package com.tishukov.planner.events.ui.create

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseEvent
import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.base.BaseViewState
import com.tishukov.planner.categories.models.Category
import com.tishukov.planner.events.model.SpendEvent
import com.tishukov.planner.extensions.now
import com.tishukov.planner.platform.randomUUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class CreateEventViewModel : BaseViewModel<CreateEventViewModel.Action, CreateEventViewModel.State, CreateEventViewModel.Event>() {

    override fun initialState() = State.NONE
    override fun onAction(action: Action) {
        //TODO("Not yet implemented")
    }

    fun selectDate(date: LocalDate?) = updateState { copy(date = date ?: LocalDate.now()) }
    fun resetState() = updateState { State.NONE }
    fun changeTitle(title: String) = updateState { copy(title = title) }
    fun changeCost(cost: String) = updateState { copy(cost = cost.toDoubleOrNull() ?: this.cost) }
    fun selectCategory(category: Category) = updateState { copy(category = category) }

    fun finish() {
        val spendEvent = with(uiState.value){
            val now = LocalDateTime.now()
            SpendEvent(
                id = randomUUID(),
                title = title,
                cost = cost,
                date = date,
                categoryId = category.id,
                createdAt = now,
                updatedAt = now
            )
        }
        resetState()
        pushEvent(Event.Finish(spendEvent))
    }

    sealed class Action : BaseAction

    data class State(
        val title: String,
        val category: Category,
        val date: LocalDate,
        val cost: Double
    ) : BaseViewState {
        companion object {
            val NONE = State(
                title = "",
                category = Category.getEmpty(),
                date = LocalDate.now(),
                cost = 0.0
            )
        }
    }

    sealed interface Event : BaseEvent {
        data class Finish(val spendEvent: SpendEvent) : Event
    }

}
