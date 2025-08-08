package com.tishukov.planner.root

import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.root.models.RootContract
import com.tishukov.planner.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel : BaseViewModel<RootContract.Action, RootContract.State, RootContract.Event>() {

    init {
        SettingsManager.themeIsDarkFlow.onEach { isDark ->
            updateState { copy(isThemeDark = isDark) }
        }.launchIn(viewModelScope)
    }

    override fun initialState(): RootContract.State = RootContract.State.getDefault()

    override fun onAction(action: RootContract.Action) {
        when (action) {
            is RootContract.Action.SelectTab -> {
                updateState { copy(selectedTab = action.appTab) }
            }
        }
    }

}
