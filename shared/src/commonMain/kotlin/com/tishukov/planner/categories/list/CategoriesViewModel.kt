package com.tishukov.planner.categories.list

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.base.BaseViewState
import com.tishukov.planner.categories.CategoriesRepository
import com.tishukov.planner.categories.models.CreateCategoryData
import com.tishukov.planner.categories.models.toCategory
import com.tishukov.planner.categories.models.Category
import com.tishukov.planner.extensions.now
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class CategoriesViewModel(
    private val repository: CategoriesRepository,
) : BaseViewModel<CategoriesViewModel.Action, CategoriesViewModel.State, Nothing>() {

    override fun initialState() = State.NONE

    override fun onAction(action: Action) {
        //TODO("Not yet implemented")
    }

    init {
        activate()
    }

    private fun activate() {
        repository.getAllFlow().onEach {
            updateState { copy(categoties = it) }
        }.launchIn(viewModelScope)
    }

    fun createCategory(data: CreateCategoryData) {
        val now = LocalDateTime.now()
        val category = data.toCategory(now)
        viewModelScope.launch {
            repository.create(category)
        }
    }

    data class State(
        val categoties: List<Category>
    ) : BaseViewState {

        companion object {
            val NONE = State(emptyList())
        }
    }

    sealed class Action : BaseAction
}
