package com.tishukov.planner.root.models

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseEvent
import com.tishukov.planner.base.BaseViewState
import com.tishukov.planner.common.ui.theme.AppPrefs

class RootContract {

    data class State(
        val isThemeDark: Boolean,
        val isFirstDayMonday: Boolean,
        val selectedTab: AppTab,
    ) : BaseViewState {

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday = isFirstDayMonday)

        companion object {
            fun getDefault(): State {
                return State(
                    isThemeDark = true,
                    isFirstDayMonday = true,
                    selectedTab = AppTab.Events,
                )
            }
        }
    }

    sealed class Event : BaseEvent

    sealed class Action : BaseAction {
        data class SelectTab(val appTab: AppTab) : Action()
    }
}
