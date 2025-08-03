package com.tishukov.planner.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Action: BaseAction, State : BaseViewState, Event : BaseEvent> : ViewModel() {

    val viewModelScope = CoroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _events = Channel<Event>()
    val events: Flow<Event> = _events.receiveAsFlow()

    fun updateState(block: State.() -> State) {
        _uiState.value = block(_uiState.value)
    }

    fun pushEvent(event: Event) {
        viewModelScope.launch { _events.send(event) }
    }

    fun onDestroy() {
        viewModelScope.cancel()
    }


    abstract fun initialState(): State
    abstract fun onAction(action: Action)

}
